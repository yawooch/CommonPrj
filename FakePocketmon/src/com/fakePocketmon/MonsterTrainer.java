package com.fakePocketmon;

import java.util.ArrayList;
import java.util.List;

public class MonsterTrainer
{ 
    private String trainerName         = "";     //트레이너 네임 
    private int experiencePoint        = 0;      //현재경험치 
    private int experiencePointMax     = 100;    //경험치상한
    private int monsterBallsMax        = 3;      //잡을수 있는 몬스터수
    private int trainerLevel           = 1;      //트레이너레벨
    private List<Monster> monsterBalls = null;   //잡을수 있는 몬스터수 
    
    public MonsterTrainer()
    {
        trainerName  = "지우";
        monsterBalls = new ArrayList<Monster>();
    }

    /**
     *  체력을 리셋한다.
     */
    public void healthReset()
    {
        for(Monster monsterBall : monsterBalls)
        {
            monsterBall.setHealthPoint(monsterBall.getHealthPointMax());
            monsterBall.setBattleStatus(true);
        }
    }
    
    /** 몬스터를 포획한다. 
     * @return boolean 몬스터 포획의 결과값 반환 true : 
     */
    public boolean catchMonster(Monster sparePartner)
    {
        double remainHealth = (double)sparePartner.getHealthPoint() / (double)sparePartner.getHealthPointMax()*100;// 남은 HP % 로 계산
        System.out.println(sparePartner.getHealthPoint()+ " / " + sparePartner.getHealthPointMax());// 남은 HP % 로 계산
        System.out.println("남은 체력 : "+ remainHealth + "%");
        //체력이 20% 초과로 남았을 때는 잡을수 없다.
        if(remainHealth > 20 && monsterBalls.size() != 0)
        {
            System.out.println("체력이 넘쳐 잡을수 없다!!");
            return false;
        }
        //몬스터볼 맥스일때
        if(monsterBalls.size() == monsterBallsMax)
        {
            System.out.println("몬스터볼을 최대로 보유하고 있어 잡을 수 없습니다");
            return false;
        }
            
        // TODO : 포획률 기능 추가하면...?
        System.out.println(sparePartner.getMonsterName() + "을(를) 잡았다!!");
        sparePartner.setBattleStatus(false);
        monsterBalls.add(sparePartner);
        return true;
    }

    /** 몬스터볼 리스트에 몬스터를 추가한다. */
    public boolean addMonster(Monster sparePartner)
    {
        monsterBalls.add(sparePartner);
        return true;
    }
    
    /** 트레이너의 이름을 가져온다. */
    public String getTtrainerName()
    {
        return trainerName;
    }
    
    /** 트레이너가 보유하고 있는 몬스터 목록을 반환한다 */
    public List<Monster> getMonsterBalls()
    {
        return monsterBalls;
    }

    /** 현재 몬스터볼의 개수를 반환한다. */
    public int getMonsterBallCount()
    {
        return monsterBalls.size();
    }

    /** 트레이너의 정보를 출력한다. */
    public String printInfo()
    {
        String rtnInfo = "";
        
        rtnInfo += "\n";
        rtnInfo += "트레이너이름 : "  + trainerName  + "\n";
        rtnInfo += "트레이너레벨 : "  + trainerLevel + "\n";
        rtnInfo += "경험치 :"         + experiencePoint +"/" + experiencePointMax  +"\n";
        rtnInfo += "잡은몬스터 수 :"  + monsterBalls.size()  +"/" + monsterBallsMax  + "\n";
        return rtnInfo;
    }


    
    /** 보유 몬스터 레벨의 평균을 구한다.
     * @return double
     */
    public double getMonstersAvgLevel()
    {
        double myMonLv    = 0;//보유 몬스터 레벨의 평균을 구한다.

        for(Monster monsterBall : monsterBalls)
        {
            myMonLv = myMonLv + monsterBall.getLevel();
        }
        myMonLv = myMonLv/monsterBalls.size();
        return myMonLv;
    }

    /**
     * @param trainerLevel the trainerLevel to set
     */
    public void setTrainerLevel(int decreseLv)
    {
        this.trainerLevel = this.trainerLevel + decreseLv;
        
        //2의 배수 레벨마다 소지가능한 몬스터볼의 수가 늘어난다. 
        if(this.trainerLevel%2 == 0 && decreseLv > 0)
        {
            this.monsterBallsMax = this.monsterBallsMax + 1;
        } 
        if(this.trainerLevel%2 == 0 && decreseLv < 0)
        {
            this.monsterBallsMax = this.monsterBallsMax - 1;
        }
    }
    
    /**
     * @param experiencePoint the experiencePoint to set
     */
    public void setExperiencePoint(Monster enemy)
    {
        int expGrowthRate = 20; //경험치 증가율
        int gainExp       = 20; // 얻은경험치
        double myMonLv    = getMonstersAvgLevel();//보유 몬스터 레벨의 평균을 구한다.
        
        //강한상대를 이기면 expGrowthRate % 더준다.
        if(myMonLv < enemy.getLevel())
        {
            gainExp = gainExp * (100+expGrowthRate)/100;
        }
        this.experiencePoint = this.experiencePoint + gainExp;
        
        int overExp = this.experiencePoint - this.experiencePointMax;
        
        System.out.println(trainerName + "는(은) 경험치 " + experiencePoint + "를 획득했다!");
        //Max 경험치를 초과하면 Level Up!
        if(overExp >= 0)
        {
            int level = this.experiencePoint/this.experiencePointMax;
            this.experiencePointMax = this.experiencePointMax * (100 + expGrowthRate)/100;//expGrowthRate만큼 expMax 증가
            this.experiencePoint = overExp;

            System.out.println(trainerName + "의 레벨이" + level + " 상승했습니다!");
            setTrainerLevel(level);
        }
    }
    
    
    /**
     * @return the trainerName
     */
    public String getTrainerName()
    {
        return trainerName;
    }

    /**
     * @param trainerName the trainerName to set
     */
    public void setTrainerName(String trainerName)
    {
        this.trainerName = trainerName;
    }

    /**
     * @return the experiencePoint
     */
    public int getExperiencePoint()
    {
        return experiencePoint;
    }

    /**
     * @return the experiencePointMax
     */
    public int getExperiencePointMax()
    {
        return experiencePointMax;
    }

    /**
     * @param experiencePointMax the experiencePointMax to set
     */
    public void setExperiencePointMax(int experiencePointMax)
    {
        this.experiencePointMax = experiencePointMax;
    }

    /**
     * @return the monsterBallsMax
     */
    public int getMonsterBallsMax()
    {
        return monsterBallsMax;
    }

    /**
     * @param monsterBallsMax the monsterBallsMax to set
     */
    public void setMonsterBallsMax(int monsterBallsMax)
    {
        this.monsterBallsMax = monsterBallsMax;
    }

    /**
     * @return the trainerLevel
     */
    public int getTrainerLevel()
    {
        return trainerLevel;
    }


    /**
     * @param monsterBalls the monsterBalls to set
     */
    public void setMonsterBalls(List<Monster> monsterBalls)
    {
        this.monsterBalls = monsterBalls;
    }
}
