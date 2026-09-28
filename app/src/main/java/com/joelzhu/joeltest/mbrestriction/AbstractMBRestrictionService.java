package com.joelzhu.joeltest.mbrestriction;

import com.joelzhu.joeltest.base.BaseCarService;
import com.joelzhu.joeltest.base.ICarService;

public abstract class AbstractMBRestrictionService<Manager> extends BaseCarService<Manager>
        implements ICarService, IMBRestrictionService {
}