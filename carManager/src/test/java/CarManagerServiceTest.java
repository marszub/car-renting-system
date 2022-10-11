import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.web3j.tuples.generated.Tuple3;
import pl.edu.agh.carManager.blockchain.RentalBlockchainProxy;
import pl.edu.agh.carManager.blockchain.TarrifBlockchainProxy;
import pl.edu.agh.carManager.carManager.dto.PricingRecord;
import pl.edu.agh.carManager.carManager.service.CarManagerService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class CarManagerServiceTest {

    @Mock
    private TarrifBlockchainProxy tarrifBlockchainProxy;
    private RentalBlockchainProxy rentalBlockchainProxy;

    private CarManagerService carManagerService;

    private final BigInteger[] intArray = {BigInteger.valueOf(0),
            BigInteger.valueOf(1),
            BigInteger.valueOf(2)};

    private final List<BigInteger> intList1 = Lists.newArrayList(intArray);
    private final List<BigInteger> intList2 = Lists.newArrayList(intArray);
    private final List<BigInteger> intList3 = Lists.newArrayList(intArray);

    //@Test
    //public void
    //TODO check adding pricing
    //TODO check getting pricing
    /*@Test
    public void testAddPricing() throws Exception {
        when(tarrifBlockchainProxy.addEntry(any())).thenReturn(null);
        this.carManagerService = new CarManagerService(tarrifBlockchainProxy);
        try{
            assertThat(carManagerService.addPricing(new PricingRecord(1, 100))).isEqualTo(true);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
     */


}
