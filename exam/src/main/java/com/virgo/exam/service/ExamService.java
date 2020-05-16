package com.virgo.exam.service;

import com.virgo.common.RequestHolder;
import com.virgo.exam.dto.ExamSaveParam;
import com.virgo.exam.model.ExamPaperRecord;
import com.virgo.exam.repository.ExamPaperRecordRepository;
import com.virgo.exam.repository.PersonalExamPaperRecordRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class ExamService {
    @Resource
    private PersonalExamPaperRecordRepository personalExamPaperRecordRepository;
    @Resource
    private ExamPaperRecordRepository examPaperRecordRepository;

    public Object exam(ExamSaveParam examSaveParam) {
        return personalExamPaperRecordRepository.findById(examSaveParam.getExamId()).map(examPaper -> {
            ExamPaperRecord record ;
            if (Objects.equals(examSaveParam.getStatus(), ExamSaveParam.Status.START)) {
                //初次考试，创建考试记录
                record = new ExamPaperRecord();
                record.setUserId(Long.valueOf(RequestHolder.getMemberId()));
                record.setExamPaperId(examPaper.getExamPaperId());
                record.setStartTime(LocalDateTime.now());
                record.setPass(false);
                record.setPersonalExamPaperId(examPaper.getId());
                examPaperRecordRepository.save(record);
            } else if (Objects.equals(examSaveParam.getStatus(), ExamSaveParam.Status.EXAMIING)) {
                //考试中途自动提交试卷
            } else if (Objects.equals(examSaveParam.getStatus(), ExamSaveParam.Status.CONTINUE)) {
                //中断考试后，继续考试
            } else if (Objects.equals(examSaveParam.getStatus(), ExamSaveParam.Status.SUBMIT)) {
                //提交考试，考试完结
            }
            return null;
        }).orElse(null);
    }
}
