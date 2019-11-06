package com.lagacione.faculdademarotinhaapi.services;

import com.lagacione.faculdademarotinhaapi.domain.Estado;
import com.lagacione.faculdademarotinhaapi.dto.EstadoDTO;
import com.lagacione.faculdademarotinhaapi.repositories.EstadoRepository;
import com.lagacione.faculdademarotinhaapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstadoService {
    @Autowired
    private EstadoRepository estadoRepository;

    public List<EstadoDTO> findAll() {
        List<Estado> estadoList = this.estadoRepository.findAll();
        List<EstadoDTO> estadoDTOList = estadoList.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
        return estadoDTOList;
    }

    public Page<EstadoDTO> findPage(Integer page, Integer size, String orderBy, String direction) {
        PageRequest pageResquest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
        Page<Estado> estados = this.estadoRepository.findAll(pageResquest);
        Page<EstadoDTO> estadoDTO = estados.map(obj -> new EstadoDTO(obj));
        return estadoDTO;
    }

    public Estado find(Integer id) throws ObjectNotFoundException {
        Optional<Estado> estado = this.estadoRepository.findById(id);
        return estado.orElseThrow(() -> new ObjectNotFoundException("Estado não encontrado!"));
    }

    private Estado insert(Estado estado) {
        estado.setId(null);
        return estadoRepository.save(estado);
    }

    private Estado update(Estado estado) throws ObjectNotFoundException {
        Estado newEstado = find(estado.getId());
        this.updateData(newEstado, estado);
        return estadoRepository.save(newEstado);
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            this.estadoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível excluir este estado, pois existem registros atrelados a ele!");
        }
    }

    private Estado fromDto(EstadoDTO estadoDTO) {
        return new Estado(estadoDTO.getId(), estadoDTO.getName(), estadoDTO.getSigla());
    }

    private void updateData(Estado newEstado, Estado estado) {
        newEstado.setName(estado.getName());
        newEstado.setSigla(estado.getSigla());
    }

    public Estado salvarEstado(EstadoDTO estadoDTO, Boolean adicionar) {
        Estado estado = this.fromDto(estadoDTO);

        if (adicionar) {
            return this.insert(estado);
        }

        return this.update(estado);
    }
}
