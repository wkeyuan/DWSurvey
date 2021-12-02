package net.diaowen.common.plugs.httpclient;

import net.diaowen.common.plugs.page.Page;

public class ResultUtils {

    public static <T> Page<T> getPageByPageResult(PageResult<T> pageResult){
        Page<T> page = new Page<T>();
        Integer current = pageResult.getCurrent();
        if(current==null){
            current=1;
        }
        Integer pageSize = pageResult.getPageSize();
        if(pageSize==null){
            pageSize = 10;
        }
        page.setPageNo(current);
        page.setPageSize(pageSize);
        return page;
    }

    public static <T> PageResult<T> getPageResultByPage(Page<T> page,PageResult<T> pageResult){
        if(page!=null){
            if(pageResult==null){
                pageResult = new PageResult<T>();
            }
            pageResult.setCurrent(page.getPageNo());
            pageResult.setSuccess(true);
            pageResult.setData(page.getResult());
            pageResult.setPageSize(page.getPageSize());
            pageResult.setTotal(Integer.parseInt(page.getTotalItems()+""));
        }
        return pageResult;
    }
}
