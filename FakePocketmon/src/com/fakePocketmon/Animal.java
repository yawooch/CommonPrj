package com.fakePocketmon;

public class Animal
{
    protected String name         = "";     //이름
    protected int expCur          = 0;      //현재경험치 
    protected int expMax          = 100;    //경험치상한
    protected int healthPoint     = 0;      //생명포인트
    protected int healthPointMax  = 100;    //생명포인트 풀피
    
    /** 정보를 출력한다. */
    public String printInfo()
    {
        String rtnInfo = "";
        
        rtnInfo += "\n";
        rtnInfo += "이름          : "  + name         + "\n";
        rtnInfo += "HP            : "  + healthPoint + " / " + healthPointMax + "\n";
        rtnInfo += "경험치        : "  + expCur +"/" + expMax  +"\n";
        return rtnInfo;
    }
    
    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * @return the expCur
     */
    public int getExpCur()
    {
        return expCur;
    }
    
    /**
     * @param expCur the expCur to set
     */
    public void setExpCur(Monster enemy)
    {
    }
    
    /**
     * @return the expMax
     */
    public int getExpMax()
    {
        return expMax;
    }
    
    /**
     * @param expMax the expMax to set
     */
    public void setExpMax(int expMax)
    {
        this.expMax = expMax;
    }

    /**
     * @return the healthPoint
     */
    public int getHealthPoint()
    {
        return healthPoint;
    }

    /**
     * @param healthPoint the healthPoint to set
     */
    public void setHealthPoint(int healthPoint)
    {
        this.healthPoint = healthPoint;
    }

    /**
     * @return the healthPointMax
     */
    public int getHealthPointMax()
    {
        return healthPointMax;
    }

    /**
     * @param healthPointMax the healthPointMax to set
     */
    public void setHealthPointMax(int healthPointMax)
    {
        this.healthPointMax = healthPointMax;
    }

}
