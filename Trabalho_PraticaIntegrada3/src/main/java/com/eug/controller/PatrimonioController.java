package com.eug.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eug.entities.Patrimonio;
import com.eug.exception.ResourceNotFoundException;
import com.eug.repository.PatrimonioRepository;

@RequestMapping("/pcontroller")
@RestController
public class PatrimonioController {
	
		@Autowired
		private PatrimonioRepository patrimonioRep;
	
		// MÉTODO LISTAR
		@GetMapping("patrimonios")
		public List<Patrimonio> patrimonios() {
			
			return patrimonioRep.findAll();
		}
	
		// CONSULTAR PATRIMONIO a partir de uma ENTIDADE
		@GetMapping("/patrimonios/{id}")
		public ResponseEntity<Patrimonio> consultarPatrimonio(@PathVariable Integer id) {
	
			Patrimonio patrimonio = patrimonioRep.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Patrimonio Não encontrado: " + id));
	
			return ResponseEntity.ok(patrimonio);
		}
	
		// INSERIR PATRIMONIO
		@PostMapping("/patrimonios")
		public Patrimonio inserir(@RequestBody Patrimonio patrimonio) {
			return patrimonioRep.save(patrimonio);
		}
	
		// DELETAR USUARIO
		@DeleteMapping("/patrimonios/{id}")
		public ResponseEntity<Map<String, Boolean>> excluir(@PathVariable Integer id) {
			// apaga o patrimonio
			Patrimonio patrimonio = patrimonioRep.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Patrimonio Não Existe No Banco de Dados: " + id));
			patrimonioRep.delete(patrimonio);
	
			// mostrará que foi apagado o usuário
			Map<String, Boolean> resposta = new HashMap<>();
			resposta.put("Patrimonio excluído: ", Boolean.TRUE);
			return ResponseEntity.ok(resposta);
	
		}
		
		//ALTERAR PATRIMONIO
		@PutMapping("/patrimonios/{id}")
		public ResponseEntity<Patrimonio>alterar(@PathVariable Integer id, @RequestBody Patrimonio patrimonio){
			
			Patrimonio patri = patrimonioRep.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Patrimonio Não encontrado: " + id));
			
			patri.setIdTombamento(patrimonio.getIdTombamento());
			patri.setDataLocacao(patrimonio.getDataLocacao());
			patri.setEstadoConservacao(patrimonio.getEstadoConservacao());
			patri.setNomeClasse(patrimonio.getNomeClasse());
			patri.setNomeEspecie(patrimonio.getNomeEspecie());
			patri.setNomeMarca(patrimonio.getNomeMarca());
			patri.setUsuario(patrimonio.getUsuario());
			patri.setValorAnual(patrimonio.getValorAnual());
			patri.setValorAquisicao(patrimonio.getValorAquisicao());
			
			Patrimonio atualizado =  patrimonioRep.save(patri);
			return ResponseEntity.ok(atualizado);	
		}
}