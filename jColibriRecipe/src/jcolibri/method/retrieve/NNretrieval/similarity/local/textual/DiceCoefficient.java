package jcolibri.method.retrieve.NNretrieval.similarity.local.textual;

import java.util.HashSet;
import java.util.Set;

import jcolibri.exception.NoApplicableSimilarityFunctionException;
import jcolibri.extensions.textual.IE.representation.IEText;
import jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import jcolibri.method.retrieve.NNretrieval.similarity.local.textual.TextualSimUtils.WeightedString;

/**
 * Dice Coefficient Similarity.
 * <p>
 * This function computes: 2*|intersection(o1,o2)| / |o1|+|o2|.
 * </p>
 * <p>It is applicable to any Text object.</p>
 * <p>
 * Developed at: Robert Gordon University - Aberdeen & Facultad Inform�tica,
 * Universidad Complutense de Madrid (GAIA)
 * </p>
 * 
 * @author Juan Antonio Recio Garc�a
 * @version 2.0
 */
public class DiceCoefficient implements LocalSimilarityFunction {

	/**
	 * Applies the similarity
	 * @param caseObject
	 *            IEText
	 * @param queryObject
	 *            IEText
	 * @return the result of the similarity function
	 */ 
	public double compute(Object caseObject, Object queryObject) throws NoApplicableSimilarityFunctionException{
		if ((caseObject == null) || (queryObject == null))
			return 0;
		if (!(caseObject instanceof IEText))
			throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
		if (!(queryObject instanceof IEText))
			throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());

		IEText caseText = (IEText) caseObject;
		IEText queryText = (IEText) queryObject;
		
		Set<WeightedString> caseSet = new HashSet<WeightedString>();
		Set<WeightedString> querySet = new HashSet<WeightedString>();
		
		TextualSimUtils.expandTokensSet(caseText.getAllTokens(), queryText.getAllTokens(), caseSet, querySet);
		
		double size1 = TextualSimUtils.getSize(caseSet);
		double size2 = TextualSimUtils.getSize(querySet);
		
		caseSet.retainAll(querySet);
		double intersectionSize = TextualSimUtils.getSize(caseSet);
		
		return (2*intersectionSize) / (size1+size2);
		
	}


	
	    /* (non-Javadoc)
	     * @see jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction#isApplicable(java.lang.Object, java.lang.Object)
	     */
	    public boolean isApplicable(Object o1, Object o2)
	    {
		if((o1==null)&&(o2==null))
			return true;
		else if(o1==null)
			return o2 instanceof IEText;
		else if(o2==null)
			return o1 instanceof IEText;
		else
			return (o1 instanceof IEText)&&(o2 instanceof IEText);
	    }
}