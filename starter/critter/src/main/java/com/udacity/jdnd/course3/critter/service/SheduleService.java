package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.repository.SheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SheduleService {

    @Autowired
    private SheduleRepository sheduleRepository;
}
