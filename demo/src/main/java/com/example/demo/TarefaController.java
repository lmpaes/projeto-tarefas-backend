package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    // Criar tarefa (POST)
    @PostMapping
    public Tarefa criarTarefa(@RequestBody Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    // Listar todas as tarefas (GET)
    @GetMapping
    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll();
    }

    // Buscar tarefa por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        return tarefa.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar tarefa (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa novaTarefa) {
        return tarefaRepository.findById(id).map(tarefa -> {
            tarefa.setNome(novaTarefa.getNome());
            tarefa.setResponsavel(novaTarefa.getResponsavel());
            tarefa.setDataEntrega(novaTarefa.getDataEntrega());
            Tarefa atualizada = tarefaRepository.save(tarefa);
            return ResponseEntity.ok(atualizada);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Deletar tarefa (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (tarefaRepository.existsById(id)) {
            tarefaRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // Retorna 204 se excluiu
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 se não encontrou
        }
    }
}
