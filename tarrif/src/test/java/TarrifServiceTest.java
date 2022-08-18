import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import pl.edu.agh.tarrif.tarrif.TarrifController;
import pl.edu.agh.tarrif.tarrif.dto.PricingRecord;
import pl.edu.agh.tarrif.tarrif.service.TarrifService;
import pl.edu.agh.tarrif.tarrifContract.Tarrif;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TarrifServiceTest {

    @InjectMocks Tarrif adminTarrifService;
    @Mock RemoteFunctionCall<TransactionReceipt> mockReceipt;


    @Test
    public void testAddPricing() throws Exception {
        //adminTarrifService = mock(Tarrif.class);
        doReturn(mockReceipt).when(adminTarrifService).addEntry(any(BigInteger.class),any(BigInteger.class));
        TarrifService tarrif = new TarrifService();
        doReturn(true).when(mockReceipt).send();
        assertTrue(tarrif.addPricing(new PricingRecord(0,100)));

        doReturn(null).when(mockReceipt).send();
       assertFalse(tarrif.addPricing(new PricingRecord(1,100)));

    }
}
