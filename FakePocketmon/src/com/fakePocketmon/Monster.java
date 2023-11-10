package com.fakePocketmon;

import java.util.ArrayList;
import java.util.Arrays;

public class Monster
{
    private String  monsterName     = "";      //몬스터이름
    private int     healthPoint     = 0;       //생명포인트
    private int     healthPointMax  = 100;     //생명포인트 풀피
    private int     attackPoint     = 0;       //공격력
    private String  elementAttr     = "";      //속성
    private boolean battleStatus    = true;    //전투가능상태 : true : 전투가능 , false : 전투불능
    private int     level           = 1;       //레벨
    private int     expMax          = 100;     //경험치상한
    private int     expCur          = 0;       //현재경험치

    public Monster()
    {
        this.healthPoint  = healthPointMax;
        this.attackPoint  = 50;
        this.elementAttr  = "무속성";//임시 속성 고정값
        this.monsterName  = "이브이";//임시 속성 고정값
        this.battleStatus = true;
    }
    
    /** 상태 Monster 를 전달받아 공격 데미지를 전달한다.
     * @param enemy
     * @param damage
     * @return
     */
    public boolean attack(Monster enemy, int damage)
    {
        damage = damageCalc(enemy);
        System.out.println(monsterName + "는(은) " + enemy.getMonsterName() + "에게 " + damage + "만큼 데미지를 입혔다.");
        
        return enemy.attackedByEnemy(damage);
    }

    /** 공격받은 데미지를 전달받아 healthPoint 를 감소시킨다 */
    private boolean attackedByEnemy(int damage)
    {
        healthPoint = healthPoint - damage;
        
        if(healthPoint <= 0)
        {
            System.out.println(monsterName + "는(은) 전투불능이 되었다.");
            battleStatus = false;
        }
        
        return battleStatus;
    }
    
    /** 몬스터의 현재 상태를 출력한다 */
    public String printInfo()
    {
        String rtnInfo = "";
        
        rtnInfo += "\n";
        rtnInfo += "몬스터이름   : " + monsterName + "\n";
        rtnInfo += "level        : " + level       + "\n";
        rtnInfo += "HP           : " + healthPoint + " / " + healthPointMax + "\n";
        rtnInfo += "공격력       : " + attackPoint + "\n";
        rtnInfo += "속성         : " + elementAttr + "\n";
        rtnInfo += "경험치       : " + expCur + " / " + expMax + "\n";
        rtnInfo += "전투가능상태 : " + (battleStatus?"전투가능":"전투불가") + "\n";
        return rtnInfo;
    }
    
    /**
     * 데미지를 계산해주는 메소드
     * 일반데미지 = 몬스터공격력 - (0 ~ damageGap)
     * 속성데미지 = 일반데미지 * elemntEfftPer
     */
    public int damageCalc(Monster defenser)
    {
        ArrayList<String> element = new ArrayList<>(Arrays.asList("전기", "물", "불", "풀"));
        int[][] eachComfotable    = {{0,1,0,0},{-1,0,1,-1},{0,-1,0,1},{0,1,-1,0}};
        int damage                = 0;
        int damageGap             = 30;
        int elemntEfftPer         = 20;//%
        
        // 일반공격 범위
        int attackPointMax = attackPoint;
        if(attackPointMax - damageGap < 0)
        {
            damageGap      = attackPointMax - damageGap;
        }
        int attackRange    = (int)(Math.random()*damageGap) +1;
        
        damage = attackPointMax - attackRange;// 공격력에서 기본 0~damageGap 뺀 공격력으로 데미지를 조절한다.
        
        String attackerEle = elementAttr;
        String defenserEle = defenser.getElementAttr();
        
        //속성공격
        int eleEffect = eachComfotable[element.indexOf(attackerEle)][element.indexOf(defenserEle)];
        if(eleEffect > 0) System.out.println("효과는 굉장했다");
        if(eleEffect < 0) System.out.println("효과는 미미했다");
        damage = (int)(damage * (eleEffect==0?1:(double)(100+eleEffect*elemntEfftPer)/100));//속성공격
        
        return damage;
    }

    /** 경험치에 대한 부분은 제외하고 능력치와 레벨과 관련된 것만 상승시킨다
     * @param decreseLv the level to set
     */
    public void setLevel(int decreseLv)
    {
        int attkGrowthRate = 20;//공격력 상승률(%)
        int hpGrowthRate   = 50;//체력   상승률(%)
        int plusMinus      = decreseLv > 0?1:(decreseLv < 0?-1:0) ;//decreseLv 의 음수양수 표시

        attackPoint  = attackPoint  * (100 + plusMinus*attkGrowthRate)/100;
        hpGrowthRate = hpGrowthRate * (100 + plusMinus*attkGrowthRate)/100;
        
        this.level = this.level + decreseLv;
    }

    
    /** 상대 몬스터의 정보를 전달받아 레벨과 비교하여 경험치를 증가시킨다
     * @param expCur the expCur to set
     */
    public void setExpCur(Monster enemy)
    {
        int levelDiff     = enemy.getLevel() - level;
        int expGrowthRate = 20;//경험치 증가율(%) & 레벨업시, Max경험치 증가율(%)
        int expCur = (int)(10 * (double)((100 + expGrowthRate*levelDiff)/100));
        this.expCur = this.expCur + expCur;// 현재 경험치에 획득 경험치를 더해준다.
        int overExp = this.expCur - this.expMax; 
        
        System.out.println(monsterName + "는(은) 경험치 " + expCur + "를 획득했다!");
        //TODO : 상극 속성을 이기면 보너스...?
        //Max 경험치를 초과하면 Level Up!
        if(overExp >= 0)
        {
            int level   = this.expMax / this.expCur; 
            this.expMax = this.expMax * (100 + expGrowthRate)/100;//expGrowthRate만큼 expMax 증가
            this.expCur = overExp;

            System.out.println(monsterName + "의 레벨이" + level + " 상승했습니다!");
            setLevel(level);
        }
    }
    /*********************************** getter / setter **********************************************/
    
    /**
     * @return the monsterName
     */
    public String getMonsterName()
    {
        return monsterName;
    }

    /**
     * @param monsterName the monsterName to set
     */
    public void setMonsterName(String monsterName)
    {
        this.monsterName = monsterName;
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

    /**
     * @return the attackPoint
     */
    public int getAttackPoint()
    {
        return attackPoint;
    }

    /**
     * @param attackPoint the attackPoint to set
     */
    public void setAttackPoint(int attackPoint)
    {
        int diff =  attackPoint - this.attackPoint;
        System.out.println("공격력이 "+ diff + "으로 되었다.");
        this.attackPoint = attackPoint;
    }

    /**
     * @return the elementAttr
     */
    public String getElementAttr()
    {
        return elementAttr;
    }

    /**
     * @param elementAttr the elementAttr to set
     */
    public void setElementAttr(String elementAttr)
    {
        this.elementAttr = elementAttr;
    }

    /**
     * @return the battleStatus
     */
    public boolean isBattleStatus()
    {
        return battleStatus;
    }

    /**
     * @param battleStatus the battleStatus to set
     */
    public void setBattleStatus(boolean battleStatus)
    {
        this.battleStatus = battleStatus;
    }
    /**
     * @return the level
     */
    public int getLevel()
    {
        return level;
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
     * @return the expCur
     */
    public int getExpCur()
    {
        return expCur;
    }

    /**
     * @param expCur the expCur to set
     */
    public void setExpCur(int expCur)
    {
        this.expCur = expCur;
    }    
}
