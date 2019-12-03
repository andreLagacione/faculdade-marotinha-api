package com.lagacione.faculdademarotinhaapi.resources;

import com.lagacione.faculdademarotinhaapi.dto.*;
import com.lagacione.faculdademarotinhaapi.services.BoletimService;
import com.lagacione.faculdademarotinhaapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/boletim")
public class BoletimResource {
    @Autowired
    BoletimService boletimService;

    @RequestMapping(value="/lista", method= RequestMethod.GET)
    public ResponseEntity<List<BoletimListaDTO>> findAll() {
        return ResponseEntity.ok().body(this.boletimService.findAll());
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Page<BoletimListaDTO>> findPage(Pageable pageable) {
        return ResponseEntity.ok().body(this.boletimService.findPage(pageable));
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
    public ResponseEntity<PadraoMensagemRetornoDTO> update(
            @Valid @RequestBody BoletimDTO boletimDTO
    ) throws Exception {
        this.boletimService.salvarRegistro(boletimDTO, false);
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Boletim editado com sucesso!");
        return ResponseEntity.ok(mensagemRetorno);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<PadraoMensagemRetornoDTO> delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.boletimService.delete(id);
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Boletim removido com sucesso!");
        return ResponseEntity.ok().body(mensagemRetorno);
    }

    @RequestMapping(value="/imprimir/{id}", method= RequestMethod.GET)
    public ResponseEntity<RetornoGerarBoletimDTO> imprimir(@PathVariable Integer id) throws Exception {
        String idBoletim = this.boletimService.downloadBoletim(id);
        RetornoGerarBoletimDTO mensagemRetorno = new RetornoGerarBoletimDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Boletim gerado com sucesso!", idBoletim);
        return ResponseEntity.ok().body(mensagemRetorno);
    }
}
