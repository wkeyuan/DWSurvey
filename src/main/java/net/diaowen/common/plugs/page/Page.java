/**
 * Copyright (c) 2005-2011 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: Page.java 1547 2011-05-05 14:43:07Z calvinxiu $
 */
package net.diaowen.common.plugs.page;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.common.collect.Lists;

/**
 * 与具体ORM实现无关的分页查询结果封装.
 * 
 * @param <T> Page中记录的类型.
 *
 */
public class Page<T> extends PageRequest implements Iterable<T> {

	protected List<T> result = null;
	protected long totalItems = -1;
	
	public Page() {
	}

	public Page(PageRequest request) {
		this.pageNo = request.pageNo;
		this.pageSize = request.pageSize;
		this.countTotal = request.countTotal;
		this.orderBy = request.orderBy;
		this.orderDir = request.orderDir;
	}

	/**
	 * 获得页内的记录列表.
	 */
	public List<T> getResult() {
		return result;
	}

	/**
	 * 设置页内的记录列表.
	 */
	public void setResult(final List<T> result) {
		this.result = result;
	}

	/**
	 * 获得总记录数, 默认值为-1.
	 */
	public long getTotalItems() {
		return totalItems;
	}

	/**
	 * 设置总记录数.
	 */
	public void setTotalItems(final long totalItems) {
		this.totalItems = totalItems;
//		getSlider(this.pageNoSize);
		setStartEndPageNo();
	}

	/** 
	 * 实现Iterable接口, 可以for(Object item : page)遍历使用
	 */
	@Override
	public Iterator<T> iterator() {
		return result.iterator();
	}

	/**
	 * 根据pageSize与totalItems计算总页数.
	 */
	public int getTotalPages() {
		return (int) Math.ceil((double) totalItems / (double) getPageSize());

	}

	/**
	 * 是否还有下一页.
	 */
	public boolean hasNextPage() {
		return (getPageNo() + 1 <= getTotalPages());
	}

	/**
	 * 是否最后一页.
	 */
	public boolean isLastPage() {
		return !hasNextPage();
	}

	/**
	 * 取得下页的页号, 序号从1开始.
	 * 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (hasNextPage()) {
			return getPageNo() + 1;
		} else {
			return getPageNo();
		}
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean hasPrePage() {
		return (getPageNo() > 1);
	}

	/**
	 * 是否第一页.
	 */
	public boolean isFirstPage() {
		return !hasPrePage();
	}

	/**
	 * 取得上页的页号, 序号从1开始.
	 * 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (hasPrePage()) {
			return getPageNo() - 1;
		} else {
			return getPageNo();
		}
	}

	/**
	 * 计算以当前页为中心的页面列表,如"首页,23,24,25,26,27,末页"
	 * @param count 需要计算的列表大小
	 * @return pageNo列表 
	 */
	public List<Integer> getSlider(int count) {
		int halfSize = count / 2;
		totalPage = getTotalPages();

		int startPageNo = Math.max(getPageNo() - halfSize, 1);
		int endPageNo = Math.min(startPageNo + count - 1, totalPage);

		if (endPageNo - startPageNo < count) {
			startPageNo = Math.max(endPageNo - count, 1);
		}

		List<Integer> result = Lists.newArrayList();
		for (int i = startPageNo; i <= endPageNo; i++) {
			result.add(i);
		}
		this.pageNos=result;
		return result;
	}
	
	private int startpage;
	private int endpage;
	
	public void setStartEndPageNo(){
		totalPage = getTotalPages();
		startpage = pageNo
				- (pageNoSize % 2 == 0 ? pageNoSize / 2 - 1
						: pageNoSize / 2);
		endpage = pageNo + pageNoSize / 2;
		if (startpage < 1) {
			startpage = 1;
			if (totalPage >= pageNoSize) {
				endpage = pageNoSize;
			} else {
				endpage = totalPage;
			}
		}
		if (endpage > totalPage) {
			endpage = totalPage;
			if ((endpage - pageNoSize) > 0) {
				startpage = endpage - pageNoSize + 1;
			} else {
				startpage = 1;
			}
		}
//		return new PageIndexUtil(startpage, endpage);
	}
	
	public int getStartpage() {
		return startpage;
	}

	public void setStartpage(int startpage) {
		this.startpage = startpage;
	}

	public int getEndpage() {
		return endpage;
	}

	public void setEndpage(int endpage) {
		this.endpage = endpage;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}


	public void addResult(T resultItem){
		if (this.result==null) {
			this.result = new ArrayList<>();
		}
		this.result.add(resultItem);
	}

	private String scrollId;

	public String getScrollId() {
		return scrollId;
	}

	public void setScrollId(String scrollId) {
		this.scrollId = scrollId;
	}
}
