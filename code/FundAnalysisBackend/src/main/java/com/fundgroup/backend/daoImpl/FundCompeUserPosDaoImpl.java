package com.fundgroup.backend.daoImpl;

import com.fundgroup.backend.dao.FundCompeUserPosDao;
import com.fundgroup.backend.entity.FundCompeUserPos;
import com.fundgroup.backend.repository.FundCompeUserPosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class FundCompeUserPosDaoImpl implements FundCompeUserPosDao {
    @Autowired
    private FundCompeUserPosRepository fundCompeUserPosRepository;

}
