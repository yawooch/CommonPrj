package com.fakePocketmon;

import java.util.ArrayList;
import java.util.List;

public class MonsterTrainer extends Animal implements LevelUpable
{ 
    private int monsterBallsMax        = 3;      //잡을수 있는 몬스터수
    private int level                  = 1;      //트레이너레벨
    private List<Monster> monsterBalls = null;   //잡을수 있는 몬스터수 
    
    public MonsterTrainer()
    {
        super.name         = "지우";
        monsterBalls = new ArrayList<Monster>();
    }
    public MonsterTrainer(String name)
    {
        super.name         = name;
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
        //이미 주인이 있는경우
        if(!sparePartner.getTrainerName().equals(""))
        {
            System.out.println("이미 트레이너가 있는 몬스터는 잡을수 없다!!\n"+ sparePartner.getTrainerName() + " : 뭐하는 짓이야!");
            return false;
        }
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
        System.out.println(sparePartner.getName() + "을(를) 잡았다!!");
        sparePartner.setBattleStatus(false);
        addMonster(sparePartner);
        return true;
    }

    /** 몬스터볼 리스트에 몬스터를 추가한다. */
    public boolean addMonster(Monster sparePartner)
    {
        monsterBalls.add(sparePartner);
        sparePartner.setTrainerName(getName());
        return true;
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
        
        rtnInfo += super.printInfo();
        rtnInfo += "트레이너레벨  : "  + level + "\n";
        rtnInfo += "잡은몬스터 수 : "  + monsterBalls.size()  +"/" + monsterBallsMax  + "\n";
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
     * @param level the trainerLevel to set
     */
    public void setLevel(int decreseLv)
    {
        this.level = this.level + decreseLv;
        
        //2의 배수 레벨마다 소지가능한 몬스터볼의 수가 늘어난다. 
        if(this.level%2 == 0 && decreseLv > 0)
        {
            this.monsterBallsMax = this.monsterBallsMax + 1;
        } 
        if(this.level%2 == 0 && decreseLv < 0)
        {
            this.monsterBallsMax = this.monsterBallsMax - 1;
        }
    }
    
    /**
     * @param expCur the expCur to set
     */
    public void setExpCur(Object enemy)
    {
        int enemyLevel = 0;
        if(enemy instanceof Monster)
        {
            enemyLevel = ((Monster)enemy).getLevel();
        }
        if(enemy instanceof MonsterTrainer)
        {
            enemyLevel = ((MonsterTrainer)enemy).getLevel();
        }
        
        int expGrowthRate = 10; //경험치 증가율
        int gainExp       = 10; // 얻은경험치
        double myMonLv    = getMonstersAvgLevel();//보유 몬스터 레벨의 평균을 구한다.
        
        //강한상대를 이기면 expGrowthRate % 더준다.
        if(myMonLv < enemyLevel)
        {
            gainExp = gainExp * (100+expGrowthRate)/100;
        }
        this.expCur = this.expCur + gainExp;
        
        int overExp = this.expCur - this.expMax;
        
        System.out.println(name        + "는(은) 경험치 " + gainExp + "를 획득했다!");
        //Max 경험치를 초과하면 Level Up!
        if(overExp >= 0)
        {
            int level = this.expCur/this.expMax;
            this.expMax = this.expMax * (100 + expGrowthRate)/100;//expGrowthRate만큼 expMax 증가
            this.expCur = overExp;

            System.out.println(name        + "의 레벨이" + level + " 상승했습니다!");
            setLevel(level);
        }
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
    public int getLevel()
    {
        return level;
    }

    /**
     * @param monsterBalls the monsterBalls to set
     */
    public void setMonsterBalls(List<Monster> monsterBalls)
    {
        this.monsterBalls = monsterBalls;
    }
}
