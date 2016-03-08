package com.eastflag.game.core.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.stereotype.Repository;

import com.eastflag.game.core.service.impl.PropertiesService;

//@Repository("commonDao")
public class CommonDaoImpl extends SqlSessionDaoSupport implements CommonDao {
	private final Logger logger = LoggerFactory.getLogger(CommonDaoImpl.class.getName());
	
	//private PropertiesService propertiesService;
	private Properties appProperties;
	private SimpleDateFormat formatDate;
	private SimpleDateFormat formatDateTime;

	public CommonDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	//@Resource(name="propertiesService")
	public void setAppProperties(Properties properties) {
		//this.appProperties = properties;
		
		this.appProperties = properties;
		
		this.formatDate = new SimpleDateFormat(properties.getProperty("format.date"));
		this.formatDateTime = new SimpleDateFormat(properties.getProperty("format.datetime"));
	}

	@Override
	public <E> List<E> selectList(String queryId) {
		logger.debug("## queryId: " + queryId);
		return selectList(queryId, null);
	}

	@Override
	public <E> List<E> selectList(String queryId, Object objParam) {
		logger.debug("## queryId: " + queryId);
		List list;
		if (objParam == null)
			list = super.getSqlSession().selectList(queryId);
		else {
			list = super.getSqlSession().selectList(queryId, objParam);
		}

		boolean isChanged = false;

		for (Iterator i$ = list.iterator(); i$.hasNext();) {
			Object obj = i$.next();
			DataMap dataMap;
			if ((obj != null) && ((obj instanceof DataMap))) {
				dataMap = (DataMap) obj;

				for (Map.Entry map : dataMap.entrySet()) {
					if ((map.getValue() instanceof Date)) {
						dataMap.put((String) map.getKey(), this.formatDate.format(map.getValue()));
						isChanged = true;
					}

					if ((map.getValue() instanceof Timestamp)) {
						dataMap.put((String) map.getKey(), this.formatDateTime.format(map.getValue()));
						isChanged = true;
					}
				}
			}

			if (!isChanged) {
				break;
			}
		}

		return list;
	}

	@Override
	public int insert(String queryId, Object paramObject) {
		logger.debug("## queryId: " + queryId);
		return getSqlSession().insert(queryId, paramObject);
	}

	@Override
	public <E> E selectOne(String queryId, Object paramObject, Class<?> clazz) {
		Object objResult;
		if (paramObject == null)
			objResult = super.getSqlSession().selectOne(queryId);
		else {
			objResult = super.getSqlSession().selectOne(queryId, paramObject);
		}

		E obReturn = null;
		DataMap dataMap;
		if ((clazz != null) && (objResult != null)) {
			obReturn = (E) clazz.cast(objResult);

			if ((obReturn instanceof DataMap)) {
				dataMap = (DataMap) obReturn;

				for (Map.Entry map : dataMap.entrySet()) {
					if ((map.getValue() instanceof Date)) {
						dataMap.put((String) map.getKey(), this.formatDate.format(map.getValue()));
					}

					if ((map.getValue() instanceof Timestamp)) {
						dataMap.put((String) map.getKey(), this.formatDateTime.format(map.getValue()));
					}
				}
			}
		}

		return obReturn;
	}

//	@Override
//	public PagingListInfo selectPagingList(String selectQueryId, String countQueryId, Object objParam) {
//		int pageUnit = this.propertiesService.getInt("paging.pageUnit");
//		int pageSize = this.propertiesService.getInt("paging.pageSize");
//
//		return selectPagingList(selectQueryId, countQueryId, objParam, pageUnit, pageSize);
//	}

//	@Override
//	public PagingListInfo selectPagingList(String selectQueryId, String countQueryId, Object objParam, int pageUint,
//			int pageSize) {
//		int currentPageNo = 0;
//
//		PaginationInfo paginationInfo = new PaginationInfo();
//		paginationInfo.setRecordCountPerPage(pageUint);
//		paginationInfo.setPageSize(pageSize);
//
//		if ((objParam instanceof Map)) {
//			Map map = (Map) objParam;
//			try {
//				currentPageNo = Integer.parseInt(map.get("pageIndex") + "");
//			} catch (Exception e) {
//				currentPageNo = 1;
//			}
//
//			paginationInfo.setCurrentPageNo(currentPageNo);
//
//			map.put("firstIndex", Integer.valueOf(paginationInfo.getFirstRecordIndex()));
//			map.put("lastIndex", Integer.valueOf(paginationInfo.getLastRecordIndex()));
//			map.put("recordCountPerPage", Integer.valueOf(paginationInfo.getRecordCountPerPage()));
//		} else {
//			try {
//				Method method = objParam.getClass().getDeclaredMethod(ClassUtil.replaceGetPropertyName("pageIndex"),
//						new Class[0]);
//				currentPageNo = ((Integer) method.invoke(objParam, new Object[0])).intValue();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			paginationInfo.setCurrentPageNo(currentPageNo);
//			try {
//				Method setFirstIndex = objParam.getClass().getDeclaredMethod(
//						ClassUtil.replaceSetPropertyName("firstIndex"), new Class[] { Integer.TYPE });
//				Method setLastIndex = objParam.getClass()
//						.getDeclaredMethod(ClassUtil.replaceSetPropertyName("lastIndex"), new Class[] { Integer.TYPE });
//				Method setRecordCountPerPage = objParam.getClass().getDeclaredMethod(
//						ClassUtil.replaceSetPropertyName("recordCountPerPage"), new Class[] { Integer.TYPE });
//
//				setFirstIndex.invoke(objParam, new Object[] { Integer.valueOf(paginationInfo.getFirstRecordIndex()) });
//				setLastIndex.invoke(objParam, new Object[] { Integer.valueOf(paginationInfo.getLastRecordIndex()) });
//				setRecordCountPerPage.invoke(objParam,
//						new Object[] { Integer.valueOf(paginationInfo.getRecordCountPerPage()) });
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		List list = selectList(selectQueryId, objParam);
//		int totalCount = count(countQueryId, objParam);
//		paginationInfo.setTotalRecordCount(totalCount);
//
//		PagingListInfo pagingListInfo = new PagingListInfo();
//		pagingListInfo.setPaginationInfo(paginationInfo);
//		pagingListInfo.setSelectList(list);
//
//		return pagingListInfo;
//	}

	@Override
	public int count(String queryId, Object objParam) {
		logger.debug("## queryId: " + queryId);
		Integer count = (Integer) selectOne(queryId, objParam, Integer.class);
		return count.intValue();
	}

	@Override
	public int update(String queryId, Object objParam) {
		logger.debug("## queryId: " + queryId);
		return getSqlSession().update(queryId, objParam);
	}

	@Override
	public DataMap selectOne(String queryId, Object objParam) {
		logger.debug("## queryId: " + queryId);
		return (DataMap) selectOne(queryId, objParam, DataMap.class);
	}

	@Override
	public int delete(String queryId, Object objParam) {
		return getSqlSession().delete(queryId, objParam);
	}

}
