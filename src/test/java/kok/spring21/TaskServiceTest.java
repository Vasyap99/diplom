package kok.spring21;

import kok.spring21.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kok.spring21.models.Task;
import kok.spring21.dto.TaskDto;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.authentication.BadCredentialsException;

import kok.spring21.util.ResponseException;



import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;



@Service
public class TaskServiceTest {
    @Test
    public void setStatus(){
        System.out.println(">>TEST");
        assertEquals(true,true);
    }
}