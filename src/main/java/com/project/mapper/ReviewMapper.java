package com.project.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.Review;



@Mapper
public interface ReviewMapper {

    // 좋아요 증가
    public int reviewlikes(Map<String, Object> recvMap);

    // 싫어요 증가
    public int reviewreport(Map<String, Object> recvMap);

    // 싫어요 개수 세기
    public Long reviewreportselect(long review_no);

    // 일정 개수의 신고를 받으면 삭제
    public int reviewreportdelete(long review_no);

    // 좋아요 -1개
    public int reviewlikesminus(Map<String, Object> recvMap);

    // public List<Review> findprofileNoNickname(long profileno);

    
}
