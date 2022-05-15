package com.epam.cleandesign;


import com.epam.cleandesign.domain.Customer;
import com.epam.cleandesign.domain.CustomerRepository;
import com.epam.cleandesign.exceptions.NotEligibleForMortgageException;
import com.epam.cleandesign.exceptions.WrongDataException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;


@RunWith(value = Enclosed.class)
public class MortgageApplicationQueueProcessorTest {


    @RunWith(value = Parameterized.class)
    public static class HappyPathTest {

        private static final double DELTA = 0.0001;

        @Parameterized.Parameter(value = 0)
        public int customerId;

        @Parameterized.Parameter(value = 1)
        public double balance;

        @Parameterized.Parameter(value = 2)
        public int badCreditHistoryCount;

        @Parameterized.Parameter(value = 3)
        public double amountRequested;

        @Parameterized.Parameter(value = 4)
        public double expected;


        @Parameterized.Parameters(name = "{index}: customerId {0}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {1, 1000, 0, 500, 1500},
                    {2, 240, 0, 100, 340},
                    {3, 0, 0, 400, 0},
                    {4, 500, 1, 1000, 500}
            });
        }

        @Test
        public void GivenACustomerId_WhenIsValid_ThenRequestIsProcessed() {

            Customer customer = new Customer(customerId, "first", "last", balance, (byte) badCreditHistoryCount);

            CustomerRepository customerRepositoryMock = Mockito.mock(CustomerRepository.class);
            Mockito.when(customerRepositoryMock.get(customerId)).thenReturn(customer);

            process(customerId, amountRequested, customerRepositoryMock);

            Assert.assertEquals(expected, customer.getBalance().doubleValue(), DELTA);

        }

    }

    public static class UnhappyPathTest {

        @Test(expected = WrongDataException.class)
        public void GivenACustomerId_WhenNotValid_ThenRequestFails() {

            int customerId = 1000;
            double amountRequested = 1500;

            CustomerRepository customerRepositoryMock =  Mockito.mock(CustomerRepository.class);
            Mockito.when(customerRepositoryMock.get(customerId)).thenReturn(null);

            process(customerId, amountRequested, customerRepositoryMock);
        }
    }

    private static void process(int customerId, double amountRequested, CustomerRepository customerRepositoryMock) {
        MortgageApplicationQueueProcessor processor = new MortgageApplicationQueueProcessor(customerRepositoryMock);
        try {
            processor.processRequest(customerId, amountRequested);
        }
        catch (NotEligibleForMortgageException e){

        }
    }


}
