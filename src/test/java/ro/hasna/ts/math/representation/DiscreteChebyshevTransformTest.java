package ro.hasna.ts.math.representation;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ro.hasna.ts.math.util.TimeSeriesPrecision;

/**
 * @since 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class DiscreteChebyshevTransformTest {
    @InjectMocks
    private DiscreteChebyshevTransform discreteChebyshevTransform;

    @Mock
    private FastFourierTransformer fastFourierTransformer;

    @Before
    public void setUp() throws Exception {
        Mockito.when(fastFourierTransformer.transform(Mockito.<double[]>any(), Mockito.<TransformType>any())).thenReturn(new Complex[]{new Complex(0)});
    }

    @After
    public void tearDown() throws Exception {
        discreteChebyshevTransform = null;
        fastFourierTransformer = null;
    }

    @Test
    public void testTransform() throws Exception {
        double[] v = {1, 2, 3};
        discreteChebyshevTransform.transform(v);

        Mockito.verify(fastFourierTransformer).transform(new double[]{1, 2, 3, 2}, TransformType.FORWARD);
    }

    @Test
    public void testTransform2() throws Exception {
        double[] v = {1, 2, 3, 4};
        discreteChebyshevTransform.transform(v);

        Mockito.verify(fastFourierTransformer).transform(new double[]{1, 2, 3, 4, 3, 2, 0, 0}, TransformType.FORWARD);
    }

    @Test
    public void testTransformSmallVector() throws Exception {
        double[] v = {1, 2};
        discreteChebyshevTransform.transform(v);

        Mockito.verify(fastFourierTransformer, Mockito.never()).transform(Mockito.<double[]>any(), Mockito.<TransformType>any());
    }

    @Test
    public void testTransformConcrete() throws Exception {
        double[] v = {98, 100}; // 99 * x^2 - x
        double[] expected = {99, -1};
        double[] results = new DiscreteChebyshevTransform().transform(v);

        Assert.assertArrayEquals(expected, results, TimeSeriesPrecision.EPSILON);
    }
}