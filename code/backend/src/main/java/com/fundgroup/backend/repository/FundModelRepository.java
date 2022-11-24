package com.fundgroup.backend.repository;

import com.fundgroup.backend.entity.FundModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundModelRepository extends JpaRepository<FundModel, Integer> {
  }
