package pn.eric.design.pattern.chain;

/**
 * @author Shadow
 * @date
 */
public class Chain implements IChain{
    String name;

    private IChain chainNext;

    public Chain(String name){
        this.name = name;
    }

    public void setChain(IChain chainNext){
        this.chainNext = chainNext;
    }




    @Override
    public void doJob() {
        System.out.println(name+" do job");
        if(chainNext!=null){
            chainNext.doJob();
        }
    }


}
