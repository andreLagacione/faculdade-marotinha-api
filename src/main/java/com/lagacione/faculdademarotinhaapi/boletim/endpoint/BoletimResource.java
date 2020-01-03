package com.lagacione.faculdademarotinhaapi.boletim.endpoint;

import com.lagacione.faculdademarotinhaapi.boletim.model.BoletimDTO;
import com.lagacione.faculdademarotinhaapi.boletim.model.BoletimFilter;
import com.lagacione.faculdademarotinhaapi.boletim.model.BoletimListaDTO;
import com.lagacione.faculdademarotinhaapi.boletim.model.BoletimToEditDTO;
import com.lagacione.faculdademarotinhaapi.boletim.service.BoletimService;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ObjectNotFoundException;
import com.lagacione.faculdademarotinhaapi.commons.models.PadraoMensagemRetornoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/boletim")
public class BoletimResource {
    private BoletimService boletimService;

    @Autowired
    public void BoletimResource(BoletimService boletimService) {
        this.boletimService = boletimService;
    }

    @GetMapping(value="/combo-list")
    public List<BoletimListaDTO> findAll() {
        return this.boletimService.findAll();
    }

    @PostMapping(value="/list")
    public Page<BoletimListaDTO> findPage(@RequestBody BoletimFilter filtro, Pageable pageable) {
        return this.boletimService.findPage(pageable, filtro);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<BoletimToEditDTO> find(@PathVariable Integer id) throws ObjectNotFoundException {
        return ResponseEntity.ok().body(this.boletimService.find(id));
    }

    @PostMapping
    public ResponseEntity<PadraoMensagemRetornoDTO> insert(@Valid @RequestBody BoletimDTO boletimDTO) throws Exception {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(this.boletimService.salvarRegistro(boletimDTO, true).getId()).toUri();
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Boletim adicionado com sucesso!");
        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    @PutMapping
    public PadraoMensagemRetornoDTO update(
            @Valid @RequestBody BoletimDTO boletimDTO
    ) throws Exception {
        this.boletimService.salvarRegistro(boletimDTO, false);
        return new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Boletim editado com sucesso!");
    }

    @DeleteMapping(value="/{id}")
    public PadraoMensagemRetornoDTO delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.boletimService.delete(id);
        return new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Boletim removido com sucesso!");
    }

    @GetMapping(value="/gerar/{id}")
    public void gerar(@PathVariable Integer id, HttpServletResponse response) throws Exception {
        this.boletimService.gerarBoletim(id, response);
    }
}
