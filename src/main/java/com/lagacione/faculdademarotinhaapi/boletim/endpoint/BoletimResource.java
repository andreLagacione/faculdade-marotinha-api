package com.lagacione.faculdademarotinhaapi.boletim.endpoint;

import com.lagacione.faculdademarotinhaapi.boletim.model.BoletimDTO;
import com.lagacione.faculdademarotinhaapi.boletim.model.BoletimListaDTO;
import com.lagacione.faculdademarotinhaapi.boletim.model.BoletimToEditDTO;
import com.lagacione.faculdademarotinhaapi.commons.models.PadraoMensagemRetornoDTO;
import com.lagacione.faculdademarotinhaapi.boletim.service.BoletimService;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ObjectNotFoundException;
import com.sun.org.apache.regexp.internal.RE;
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

    @RequestMapping(value="/lista", method= RequestMethod.GET)
    public List<BoletimListaDTO> findAll() {
        return this.boletimService.findAll();
    }

    @RequestMapping(method=RequestMethod.GET)
    public Page<BoletimListaDTO> findPage(Pageable pageable) {
        return this.boletimService.findPage(pageable);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<BoletimToEditDTO> find(@PathVariable Integer id) throws ObjectNotFoundException {
        return ResponseEntity.ok().body(this.boletimService.find(id));
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<PadraoMensagemRetornoDTO> insert(@Valid @RequestBody BoletimDTO boletimDTO) throws Exception {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(this.boletimService.salvarRegistro(boletimDTO, true).getId()).toUri();
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Boletim adicionado com sucesso!");
        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    @RequestMapping(method=RequestMethod.PUT)
    public PadraoMensagemRetornoDTO update(
            @Valid @RequestBody BoletimDTO boletimDTO
    ) throws Exception {
        this.boletimService.salvarRegistro(boletimDTO, false);
        return new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Boletim editado com sucesso!");
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public PadraoMensagemRetornoDTO delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.boletimService.delete(id);
        return new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Boletim removido com sucesso!");
    }

    @RequestMapping(value="/gerar/{id}", method= RequestMethod.GET)
    public void gerar(@PathVariable Integer id, HttpServletResponse response) throws Exception {
        this.boletimService.gerarBoletim(id, response);
    }
}
