package com.lagacione.faculdademarotinhaapi.nota.endpoint;

import com.lagacione.faculdademarotinhaapi.commons.exceptions.ObjectNotFoundException;
import com.lagacione.faculdademarotinhaapi.commons.models.PadraoMensagemRetornoDTO;
import com.lagacione.faculdademarotinhaapi.nota.model.NotaDTO;
import com.lagacione.faculdademarotinhaapi.nota.model.NotaListDTO;
import com.lagacione.faculdademarotinhaapi.nota.service.NotaService;
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
@RequestMapping(value="/nota")
public class NotaResource {
    private NotaService notaService;

    @Autowired
    public void MateriaNotaBimestreResource(NotaService notaService) {
        this.notaService = notaService;
    }

    @GetMapping(value="/combo-list")
    public List<NotaListDTO> findAll() {
        return this.notaService.findAll();
    }

    @GetMapping(value="/id-boletim/{idBoletim}")
    public List<NotaListDTO> findByBoletimId(@PathVariable Integer idBoletim) {
        return this.notaService.findByBoletimId(idBoletim);
    }

    @GetMapping(value="/{id}")
    public NotaDTO find(@PathVariable Integer id) throws ObjectNotFoundException {
        return this.notaService.find(id);
    }

    @PostMapping
    public ResponseEntity<PadraoMensagemRetornoDTO> insert(
            @Valid @RequestBody NotaDTO notaDTO
    ) throws Exception {
        URI uri;
        uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(this.notaService.salvarRegistro(notaDTO, true).getId()).toUri();
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Nota adicionada com sucesso!");
        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    @PutMapping
    public PadraoMensagemRetornoDTO update(
            @Valid @RequestBody NotaDTO notaDTO
    ) throws Exception {
        this.notaService.salvarRegistro(notaDTO, false);
        return new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Nota editada com sucesso!");
    }

    @DeleteMapping(value="/{id}")
    public PadraoMensagemRetornoDTO delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.notaService.delete(id, false);
        return new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Nota removida com sucesso!");
    }
}
