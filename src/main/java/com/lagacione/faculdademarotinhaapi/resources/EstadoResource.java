package com.lagacione.faculdademarotinhaapi.resources;

import com.lagacione.faculdademarotinhaapi.domain.Estado;
import com.lagacione.faculdademarotinhaapi.dto.EstadoDTO;
import com.lagacione.faculdademarotinhaapi.dto.PadraoMensagemRetorno;
import com.lagacione.faculdademarotinhaapi.services.EstadoService;
import com.lagacione.faculdademarotinhaapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/estado")
public class EstadoResource {

    @Autowired
    private EstadoService estadoService;

    @RequestMapping(value="/lista", method=RequestMethod.GET)
    public ResponseEntity<List<EstadoDTO>> findAll() {
        return ResponseEntity.ok().body(this.estadoService.findAll());
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Page<EstadoDTO>> findPage(
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="size", defaultValue="25") Integer size,
            @RequestParam(value="orderBy", defaultValue="nome") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction
    ) {
        return ResponseEntity.ok().body(this.estadoService.findPage(page, size, orderBy, direction));
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Estado> find(@PathVariable Integer id) throws ObjectNotFoundException {
        return ResponseEntity.ok().body(this.estadoService.find(id));
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<PadraoMensagemRetorno> insert(@Valid @RequestBody EstadoDTO estadoDTO) {
        Estado estado = this.estadoService.salvarEstado(estadoDTO, true);
        PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Estado criado com sucesso!");

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(estado.getId()).toUri();

        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<PadraoMensagemRetorno> update(
            @Valid @RequestBody EstadoDTO estadoDTO
    ) throws ObjectNotFoundException {
        this.estadoService.salvarEstado(estadoDTO, false);
        PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Estado editado com sucesso!");
        return ResponseEntity.ok().body(mensagemRetorno);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<PadraoMensagemRetorno> delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.estadoService.delete(id);
        PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Estado removido com sucesso!");
        return ResponseEntity.ok().body(mensagemRetorno);
    }

}
