package com.project.service.KDH;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.dto.Actorsdto;
import com.project.dto.Memberdto;
import com.project.dto.VideolistView;
import com.project.dto.Videolistdto;
import com.project.entity.Videoimg;
import com.project.entity.Videolist;
import com.project.mapper.KDH.KDHMapper;
import com.project.repository.MemberRepository;
import com.project.repository.VideoimgRepository;
import com.project.repository.videolistRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DHServiceImpl implements DHService {
    final videolistRepository videolistRepository;
    final VideoimgRepository videoimgRepository;
    final MemberRepository memberRepository;
    final KDHMapper kdhMapper;

    // 회원의 권한을 받아 검증후 비디오 추가 회차정보를 입력받으면 그횟수만큼 비디오횟차를 올려서 추가
    @Override
    public void videolistInsert(String role, Videolistdto obj) {
        // 아이디를 입력받으면 로그인파트랑 연계
        // Member member=memberRepository.findById(admin.getId()).orElse(null);
        // if(member.getRole().equals("a")){
        int episodecount = obj.getEpisode().intValue();
        if (role.equals("A")) {
            for (int i = 0; i < episodecount; i++) {
                obj.setEpisode(Long.valueOf(i + 1));
                kdhMapper.videolistInsert(obj);
            }
        }
    }
    // 비디오의 이름을 받아서 작품코드를 조회 episode가 1인것조회
    @Override
    public Videolist selectnofromtitle(String title) {
        // System.out.println(videolistRepository.findByTitleAndEpisode(title,
        // BigInteger.valueOf(1)).getVideocode());
        return videolistRepository.findByTitleAndEpisode(title, BigInteger.valueOf(1));
    }
    // 조회된 비디오 코드로 비디오 리스트 뷰에서 비디오하나 조회
    @Override
    public VideolistView selectvideoOne(BigInteger videocode) {
        // System.out.println(kdhMapper.selectVideoOne(videocode).toString());
        return kdhMapper.selectVideoOne(videocode);

    }
    // 회원의 권한 확인후 관리자 확인되면 작품의 이름똑같은 모든 비디오를 수정함
    @Override
    public void videolistUpdate(Memberdto admin, Videolistdto obj, String nowtitle) {
        // 아이디를 입력받으면 로그인파트랑 연계
        // Member member=memberRepository.findById(admin.getId()).orElse(null);
        // if(member.getRole().equals("a")){
            kdhMapper.videolistUpdate(obj, nowtitle);
    }
    @Override
    public void videolistDelete(Memberdto admin, String title) {
        // 아이디를 입력받으면 로그인파트랑 연계
        // Member member=memberRepository.findById(admin.getId()).orElse(null);
        // if(member.getRole().equals("a")){
            videolistRepository.deleteByTitle(title);
    }


    @Override
    public List<Videolistdto> selectvideolist() {
        return kdhMapper.selectvideolist();
    }
    @Override
    public int addactorinvideo(Videolistdto videocode, Actorsdto no) {
    //    Long epi = videocode.getEpisode();
    //    for(int i=0; i<epi; i++){
        kdhMapper.castsInsertactor(videocode.getVideocode(), no.getActors_No());
    //    }
       return 1;
    }
    @Override
    public int removeactorinvideo(Videolistdto videocode, Actorsdto no) {
        return kdhMapper.castsDeleteactor(videocode.getVideocode(), no.getActors_No());
    }
    @Override
    public List<Actorsdto> selectactors() {
        return kdhMapper.selectActors();
    }
    @Override
    public int addactorlist(String name) {
        return kdhMapper.actorInsert(name);
    }
    @Override
    public List<Long> selectactorsinvideo(BigInteger videocode) {
        return kdhMapper.selectActorsinvideo(videocode);
    }
    @Override
    public Actorsdto selectnotoname(Long no) {
        return kdhMapper.selectnotoname(no);
    }
    @Override
    public int castsInsertactorchk(Long actors_no, Long videocode) {
        return kdhMapper.castsInsertactorchk(actors_no, videocode);
    }
    @Override
    public List<Videolist> selectvideofordelete(String title) {
      List<Videolist> list=videolistRepository.findByTitle(title);
      return list;
    }
    @Override
    public Long selectvideoimgOne(Long videocode) {
        // System.out.println(kdhMapper.selectimgnotovideocode(videocode));
        return kdhMapper.selectimgnotovideocode(videocode);
    }
   
   
   
    @Override
    public List<VideolistView> videolistSearch(String comboboxvalue, String search) {
        return kdhMapper.videolistSearch(comboboxvalue, search);
    }
    @Override
    public List<Videolistdto> videolistGroupSearch(String category) {
       return kdhMapper.videolistGroupSearch(category);
    }
    @Override
    public List<Videolistdto> videolistGroupKeywordButton(String category, String genre) {
        return kdhMapper.videolistGroupKeywordButton(category, genre);
    }
    @Override
    public List<Videolist> videolistRecently(BigInteger episode) {
        
         return videolistRepository.findByEpisodeOrderByOpendateDesc(episode);
    }
    @Override
    public int videolistCHKage(Long videocode, Memberdto member) {
      String chkage= kdhMapper.videolistCHKage(videocode).getChkage(); //연령제한이 나옴
        String year=member.getBirth().substring(0, 4);
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int nowyear =cal.get(Calendar.YEAR);
        if((nowyear)-Integer.parseInt(year)>=12 && (nowyear)-Integer.parseInt(year)<15){
            //전체 관람가 랑 12세이용가만 가능 볼수있게 아니면 시청불가 떠야됨
            if(chkage.equals("청소년관람불가") || chkage.equals("15세관람가")){
                return 0;
            }
            else{
                return 1;
            }
        }
       else if((nowyear)-Integer.parseInt(year)>=15 && (nowyear)-Integer.parseInt(year)<19){
        if(chkage.equals("청소년관람불가")){
            return 0;
        }
        else{
            return 1;
        }
        }
       else if((nowyear)-Integer.parseInt(year)>=19){
        return 1;
        }
        else{
            if(chkage.equals("청소년관람불가")  || chkage.equals("15세관람가") || chkage.equals("12세관람가")){
                return 0;
            }
            else{
                return 1;
            }
        }
    }

    @Override
    public int insertvideoimg(MultipartFile file, Videolist video) {
        try {
            Videoimg vi = new Videoimg();
            vi.setVideolist(video);
            vi.setFilesize(BigInteger.valueOf(file.getSize()));
            vi.setFiletype(file.getContentType());
            vi.setFiledata(file.getInputStream().readAllBytes());
            vi.setFilename(file.getOriginalFilename());
             videoimgRepository.save(vi);
             return 1;
            
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    @Override
    public int deletevideoimg(Long vimgno) {
        try {
            videoimgRepository.deleteById(BigInteger.valueOf(vimgno));
            return 1;
            
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
