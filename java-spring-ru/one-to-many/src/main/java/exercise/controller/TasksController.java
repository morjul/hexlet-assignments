package exercise.controller;

import java.util.List;
import java.util.Optional;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.mapper.TaskMapper;
import exercise.model.User;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.TaskRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    // BEGIN
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    // BEGIN
    @Autowired
    private TaskMapper taskMapper;

    @GetMapping(path = "")
    public List<TaskDTO> index() {
        var products = taskRepository.findAll();
        return products.stream()
                .map(taskMapper::map)
                .toList();
    }

    @GetMapping(path = "/{id}")
    public TaskDTO show(@PathVariable long id) {

        var task =  taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
        var taskDTO = taskMapper.map(task);
        taskDTO.setAssigneeId(task.getAssignee().getId());
        return taskDTO;
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@RequestBody TaskCreateDTO taskData) {
        var task = taskMapper.map(taskData);
        Optional<User> optionalUser = userRepository.findById(taskData.getAssigneeId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.addTask(task);
            userRepository.save(user);
        }
        var taskDTO = taskMapper.map(task);
        taskDTO.setAssigneeId(taskData.getAssigneeId());
        return taskDTO;
    }

    // BEGIN
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    TaskDTO update(@RequestBody TaskUpdateDTO taskData, @PathVariable Long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        Optional<User> optionalUser = userRepository.findById(taskData.getAssigneeId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.addTask(task);
            userRepository.save(user);
        }
        taskMapper.update(taskData, task);
        taskRepository.save(task);
        var taskDTO = taskMapper.map(task);
        taskDTO.setAssigneeId(taskData.getAssigneeId());
        return taskDTO;
    }

    @DeleteMapping("/{id}") // Удаление страницы
    public void destroy(@PathVariable long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        Optional<User> optionalUser = userRepository.findById(task.getAssignee().getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.removeTask(task);
            userRepository.save(user);
        }
    }
    // END
}
