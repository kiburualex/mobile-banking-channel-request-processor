package io.firogence.mobile_banking_channel_request_processor.services.impl;

import io.firogence.mobile_banking_channel_request_processor.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Alex Kiburu
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    @Override
    public boolean isAccountActive(String accountNumber) {
        /** todo:: perform account lookup and check if it's active. (if account not in MIB profile, perform a lookup) */
        return false;
    }

}
