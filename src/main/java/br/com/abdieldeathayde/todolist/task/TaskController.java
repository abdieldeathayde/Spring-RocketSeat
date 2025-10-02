package br.com.abdieldeathayde.todolist.task;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        // Verifica se já existe uma tarefa com o mesmo título
        var existingTask = taskRepository.findByTitle(taskModel.getTitle());
        if (existingTask != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa com este título já existe");
        }

        // Salva a nova tarefa
        var taskCreated = taskRepository.save(taskModel);
        System.out.println("Chegou no controller");
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);

    }
}