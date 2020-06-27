package com.virgo.member.service;

import com.virgo.common.RequestHolder;
import com.virgo.member.model.Category;
import com.virgo.member.repository.CategoryRepository;
import com.virgo.member.vo.CategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryService {
    @Resource
    private CategoryRepository categoryRepository;

    public List<CategoryVO> findAll() {
        List<Category> m = categoryRepository.findAllByCompanyCode(RequestHolder.getCompanyCode());
        List<CategoryVO> treeNodes = m.stream().map(it -> {
            CategoryVO vo = new CategoryVO();
            BeanUtils.copyProperties(it, vo);
            return vo;
        }).collect(Collectors.toList());

        return build(treeNodes);
    }

    private List<CategoryVO> build(List<CategoryVO> treeNodes) {
        List<CategoryVO> trees = new ArrayList<>();
        for (CategoryVO treeNode : treeNodes) {

            if (treeNode.getParentId() == null) {
                trees.add(treeNode);
            }

            for (CategoryVO it : treeNodes) {
                if (Objects.equals(it.getParentId(), treeNode.getId())) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.getChildren().add(it);
                }
            }
        }
        return trees;
    }
}
