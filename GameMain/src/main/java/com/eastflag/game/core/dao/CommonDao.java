package com.eastflag.game.core.dao;

import java.util.List;

public interface CommonDao {
	public abstract <E> List<E> selectList(String paramString);

	public abstract <E> List<E> selectList(String paramString, Object paramObject);
	
	public abstract int insert(String paramString, Object paramObject);
	
	public abstract <E> E selectOne(String paramString, Object paramObject, Class<?> paramClass);
	
	//public abstract PagingListInfo selectPagingList(String paramString1, String paramString2, Object paramObject);
	
	//public abstract PagingListInfo selectPagingList(String paramString1, String paramString2, Object paramObject, int paramInt1, int paramInt2);
	
	public abstract int update(String paramString, Object paramObject);
	
	public abstract int count(String paramString, Object paramObject);
	
	public abstract DataMap selectOne(String paramString, Object paramObject);
	
	public abstract int delete(String paramString, Object paramObject);
}
