package com.fundgroup.backend.utils;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@Transactional
public class BatchUtils {

  @PersistenceContext
  private EntityManager entityManager;

  //配置文件中每次批量提交的数量
  private long batchSize = 5000;

  /**
   * 批量更新
   *
   * @param list 实体类集合
   * @param <T>  表对应的实体类
   */
  public <T> void batchUpdate(List<T> list) {
    if (!ObjectUtils.isEmpty(list)) {
      int size = list.size();
      System.out.println("start batchUpdate size= " + size);
      for (int i = 0; i < size; i++) {
        if (i % 1000 == 0) {
          System.out.println(i);
        }
        entityManager.merge(list.get(i));
        if (i % batchSize == 0) {
          entityManager.flush();
          entityManager.clear();
        }
      }
      entityManager.flush();
      entityManager.clear();
    }
  }
}


