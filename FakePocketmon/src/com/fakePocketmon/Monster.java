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
        System.out.println(monsterName + "은(는) " + enemy.getMonsterName() + "에게 " + damage + "만큼 데미지를 입혔다.");
        
        return enemy.attackedByEnemy(damage);
    }

    /** 공격받은 데미지를 전달받아 healthPoint 를 감소시킨다 */
    private boolean attackedByEnemy(int damage)
    {
        healthPoint = healthPoint - damage;
        
        if(healthPoint <= 0)
        {
            System.out.println(monsterName + "은(는) 전투불능이 되었다.");
            battleStatus = false;
        }
        
        return battleStatus;
    }
    
    /** 몬스터의 현재 상태를 출력한다 */
    public String printInfo()
    {
        String rtnInfo = "";
        
        rtnInfo += "\n";
        rtnInfo += "몬스터이름 : "  + monsterName + "\n";
        rtnInfo += "HP :"           + healthPoint + "\n";
        rtnInfo += "공격력 :"       + attackPoint + "\n";
        rtnInfo += "속성 :"         + elementAttr + "\n";
        rtnInfo += "전투가능상태 :" + (battleStatus?"전투가능":"전투불가") + "\n";
        return rtnInfo;
    }
    /**
     * 데미지를 계산해주는 메소드
     */
    public int damageCalc(Monster defenser)
    {
        ArrayList<String> element = new ArrayList<>(Arrays.asList("전기", "물", "불", "풀"));
        int[][] eachComfotable    = {{0,1,0,0},{-1,0,1,-1},{0,-1,0,1},{0,1,-1,0}};
        int damage                = 0;
        int damageGap             = 30;
        int elemntEfftPer         = 20;
        
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
        System.out.println(damage + ", " + (eleEffect==0?1:(100+eleEffect*elemntEfftPer)/100));
        damage = (int)(damage * (eleEffect==0?1:(double)(100+eleEffect*elemntEfftPer)/100));//속성공격
        
        return damage;
    }
    
    public void levelUp()
    {
         
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
    

}
