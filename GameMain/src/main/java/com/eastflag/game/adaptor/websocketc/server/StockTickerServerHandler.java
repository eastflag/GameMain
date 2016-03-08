package com.eastflag.game.adaptor.websocketc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eastflag.game.adaptor.websocketc.NettyHttpFileHandler;
import com.eastflag.game.adaptor.websocketc.StockTickerMessageHandler;
import com.eastflag.game.adaptor.websocketc.WebSocketMessageHandler;
import com.eastflag.game.adaptor.websocketc.WebSocketServerAdaptor;
import com.eastflag.game.core.message.WebSocketCall;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;

public class StockTickerServerHandler extends SimpleChannelInboundHandler<Object> {
	
	private static final Logger logger = LoggerFactory.getLogger(StockTickerServerHandler.class.getName());
	
	private WebSocketServerAdaptor adaptor;
	
	protected WebSocketServerHandshaker handshaker;
	private   StringBuilder frameBuffer = null;
	protected WebSocketMessageHandler wsMessageHandler = new StockTickerMessageHandler();
	protected NettyHttpFileHandler httpFileHandler = new NettyHttpFileHandler();
	
	public StockTickerServerHandler(WebSocketServerAdaptor adaptor) {
		this.adaptor = adaptor;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof FullHttpRequest) {
			this.handleHttpRequest(ctx, (FullHttpRequest)msg);
		} else if (msg instanceof WebSocketFrame) {
			this.handleWebSocketFrame(ctx, (WebSocketFrame)msg);
		}
	}

	private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
		logger.debug("Received incoming frame [{}]", frame.getClass().getName());
		// Check for closing frame
		if (frame instanceof CloseWebSocketFrame) {
			if (frameBuffer != null) {
				handleMessageCompleted(ctx, frameBuffer.toString());
			}
			handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
			return;
		}

		if (frame instanceof PingWebSocketFrame) {
			ctx.channel().writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
			return;
		}

		if (frame instanceof PongWebSocketFrame) {
			logger.info("Pong frame received");
			return;
		}

		if (frame instanceof TextWebSocketFrame) {
			frameBuffer = new StringBuilder();
			frameBuffer.append(((TextWebSocketFrame) frame).text());
		} else if (frame instanceof ContinuationWebSocketFrame) {
			if (frameBuffer != null) {
				frameBuffer.append(((ContinuationWebSocketFrame) frame).text());
			} else {
				logger.warn("Continuation frame received without initial frame.");
			}
		} else {
			throw new UnsupportedOperationException(
					String.format("%s frame types not supported", frame.getClass().getName()));
		}
		
		

		// Check if Text or Continuation Frame is final fragment and handle if
		// needed.
		if (frame.isFinalFragment()) {
			handleMessageCompleted(ctx, frameBuffer.toString());
			frameBuffer = null;
		}
	}

	private void handleMessageCompleted(ChannelHandlerContext ctx, String frameText) {
//		String response = wsMessageHandler.handleMessage(ctx, frameText);
//		if (response != null) {
//			ctx.channel().writeAndFlush(new TextWebSocketFrame(response));
//		}
		
		WebSocketCall call = makeWebSocketCall(frameText);
		adaptor.dispatchCall(call);
	}

	private WebSocketCall makeWebSocketCall(String frameText) {
		WebSocketCall call = new WebSocketCall();
		
		call.setReqMessage(frameText);
		
		return call;
	}

	private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
		// Handle a bad request.
		if (!req.getDecoderResult().isSuccess()) {
			httpFileHandler.sendError(ctx, HttpResponseStatus.BAD_REQUEST);
			return;
		}

		// If you're going to do normal HTTP POST authentication before
		// upgrading the
		// WebSocket, the recommendation is to handle it right here
		if (req.getMethod() == HttpMethod.POST) {
			httpFileHandler.sendError(ctx, HttpResponseStatus.FORBIDDEN);
			return;
		}

		// Allow only GET methods.
		if (req.getMethod() != HttpMethod.GET) {
			httpFileHandler.sendError(ctx, HttpResponseStatus.FORBIDDEN);
			return;
		}

		// Send the demo page and favicon.ico
		if ("/".equals(req.getUri())) {
			httpFileHandler.sendRedirect(ctx, "/index.html");
			return;
		}

		// check for websocket upgrade request
		String upgradeHeader = req.headers().get("Upgrade");
		if (upgradeHeader != null && "websocket".equalsIgnoreCase(upgradeHeader)) {
			// Handshake. Ideally you'd want to configure your websocket uri
			String url = "ws://" + req.headers().get("Host") + "/wsticker";
			WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(url, null, false);
			handshaker = wsFactory.newHandshaker(req);
			if (handshaker == null) {
				WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
			} else {
				handshaker.handshake(ctx.channel(), req);
			}
		} else {
			boolean handled = handleREST(ctx, req);
			if (!handled) {
				httpFileHandler.sendFile(ctx, req);
			}
		}
	}

	private boolean handleREST(ChannelHandlerContext ctx, FullHttpRequest req) {
		// TODO Auto-generated method stub
		return false;
	}

}
