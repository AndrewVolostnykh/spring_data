package com.bsa.springdata.office;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfficeService {
    @Autowired
    private OfficeRepository officeRepository;

    public List<OfficeDto> getByTechnology(String technology) {
        // TODO: Use single query to get data.
        return officeRepository.getByTechnology(technology).stream().map(OfficeDto::fromEntity).collect(Collectors.toList());
    }

    public Optional<OfficeDto> updateAddress(String oldAddress, String newAddress) {
        // TODO: Use single method to update address. In order to get the new office you can make extra query
        //  Hint: Every user is connected to one of the project. There cannot be any users without a project.
        officeRepository.updateOfficeAddress(oldAddress, newAddress);
        var resultOfFind = officeRepository.findOneByAddress(newAddress);

        return resultOfFind == null ? Optional.empty() : Optional.of(OfficeDto.fromEntity(resultOfFind));
    }
}
