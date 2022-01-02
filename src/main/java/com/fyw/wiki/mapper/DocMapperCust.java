package com.fyw.wiki.mapper;

import org.apache.ibatis.annotations.Param;

public interface DocMapperCust {

    public void updateDocViewCount(@Param("id") Long id);
    public void updateDocVoteCount(@Param("id") Long id);

    public void updateEbookInfo();


}
