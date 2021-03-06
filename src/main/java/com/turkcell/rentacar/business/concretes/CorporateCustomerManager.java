package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.corporateCustomer.CorporateCustomerListDto;
import com.turkcell.rentacar.business.dtos.corporateCustomer.GetCorporateCustomerDto;
import com.turkcell.rentacar.business.requests.corporateCustomer.CreateCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.corporateCustomer.UpdateCorporateCustomerRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataaccess.abstracts.CorporateCustomerDao;
import com.turkcell.rentacar.entities.concretes.CorporateCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

    private final CorporateCustomerDao corporateCustomerDao;
    private final ModelMapperService modelMapperService;

    @Autowired
    public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService) {

        this.corporateCustomerDao = corporateCustomerDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<CorporateCustomerListDto>> getAll() {

        List<CorporateCustomer> result = this.corporateCustomerDao.findAll();

        List<CorporateCustomerListDto> response = result.stream().map(corporateCustomer -> this.modelMapperService
                .forDto().map(corporateCustomer, CorporateCustomerListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<CorporateCustomerListDto>>(response, BusinessMessages.SUCCESSFULLY_LISTED);
    }

    @Override
    public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {

        checkIfTaxNumberAlreadyExists(createCorporateCustomerRequest.getTaxNumber());

        CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);

        this.corporateCustomerDao.save(corporateCustomer);

        return new SuccessResult(BusinessMessages.SUCCESSFULLY_ADDED);
    }

    @Override
    public DataResult<GetCorporateCustomerDto> getByUserId(Integer id) throws BusinessException {

        checkIfCorporateCustomerIdExists(id);

        CorporateCustomer corporateCustomer = this.corporateCustomerDao.getById(id);

        GetCorporateCustomerDto response = this.modelMapperService.forDto().map(corporateCustomer, GetCorporateCustomerDto.class);

        return new SuccessDataResult<GetCorporateCustomerDto>(response, BusinessMessages.SUCCESSFULLY_FOUND);
    }

    @Override
    public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {

        checkIfCorporateCustomerIdExists(updateCorporateCustomerRequest.getUserId());
        checkIfTaxNumberAlreadyExists(updateCorporateCustomerRequest.getTaxNumber());

        CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest, CorporateCustomer.class);

        this.corporateCustomerDao.save(corporateCustomer);

        return new SuccessResult(BusinessMessages.SUCCESSFULLY_UPDATED);
    }

    @Override
    public Result delete(int corporateCustomerId) throws BusinessException {

        checkIfCorporateCustomerIdExists(corporateCustomerId);

        this.corporateCustomerDao.deleteById(corporateCustomerId);

        return new SuccessResult(BusinessMessages.SUCCESSFULLY_DELETED);
    }

    @Override
    public void checkIfCorporateCustomerIdExists(Integer id) throws BusinessException {

        if (!this.corporateCustomerDao.existsById(id)) {

            throw new BusinessException(BusinessMessages.NOT_FOUND);
        }
    }

    @Override
    public void checkIfTaxNumberAlreadyExists(String taxNumber) throws BusinessException {

        if (this.corporateCustomerDao.existsCorporateCustomerByTaxNumber(taxNumber)) {

            throw new BusinessException(BusinessMessages.ALREADY_EXIST);
        }
    }
}
