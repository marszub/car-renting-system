import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.tuples.generated.Tuple3;
import pl.edu.agh.tarrif.blockchain.TarrifBlockchainProxy;
import pl.edu.agh.tarrif.tarrif.dto.PricingRecord;
import pl.edu.agh.tarrif.tarrif.service.TarrifService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class TarrifServiceTest {

    @Mock
    private TarrifBlockchainProxy tarrifBlockchainProxy;

    private TarrifService tarrifService;

    private final BigInteger[] intArray = {BigInteger.valueOf(0),
            BigInteger.valueOf(1),
            BigInteger.valueOf(2)};

    private final List<BigInteger> intList1 = Lists.newArrayList(intArray);
    private final List<BigInteger> intList2 = Lists.newArrayList(intArray);
    private final List<BigInteger> intList3 = Lists.newArrayList(intArray);
    @Test
    @Transactional
    public void testAddPricing() throws Exception {
        when(tarrifBlockchainProxy.addEntry(any())).thenReturn(null);
        this.tarrifService = new TarrifService(tarrifBlockchainProxy);
        try{
            assertThat(tarrifService.addPricing(new PricingRecord(1,100))).isEqualTo(true);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void testGetPricing() throws Exception {
        when(tarrifBlockchainProxy.getPricing()).thenReturn(new Tuple3<>(intList1, intList2, intList3));
        this.tarrifService = new TarrifService(tarrifBlockchainProxy);
        try{
            ArrayList<PricingRecord> result = tarrifService.getPricing();
            assertThat(result.get(0).carType()).isEqualTo(0);
            assertThat(result.get(0).price()).isEqualTo(0);
            assertThat(result.get(1).carType()).isEqualTo(1);
            assertThat(result.get(1).price()).isEqualTo(1);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
