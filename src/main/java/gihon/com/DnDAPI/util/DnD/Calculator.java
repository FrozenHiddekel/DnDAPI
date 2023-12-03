package gihon.com.DnDAPI.util.DnD;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gihon.com.DnDAPI.util.errors.DamageNotCorrectException;
import gihon.com.DnDAPI.util.errors.SpellDamageNotCorrectException;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Calculator {
    public static HashMap<String, String> DamageShow(String damage){
        HashMap<String, String> myMap = new HashMap<>();
        try {
            String[] pairs = damage.split(",");
            for (int i=0;i<pairs.length;i++) {
                String pair = pairs[i];
                String[] keyValue = pair.split(":");
                myMap.put(keyValue[0], keyValue[1]);
            }
        } catch (RuntimeException exe){
             throw new DamageNotCorrectException();
        }
        return myMap;
    }

    public static HashMap<String, Integer> DamageCalculate(String damage) throws JsonProcessingException {
        HashMap<String,String> map =
                new ObjectMapper().readValue(damage, HashMap.class);
        HashMap<String,Integer> out = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            out.put(key, damageByOne(value.toString()));
        }
        return out;
    }

    public static int getMaxSpellSlot(int castLevel){
        return Math.min((castLevel + 1) / 2, 9);
    }
    public static HashMap<String,Object> spellDamageCalculate(HashMap<String,Object> damageMap, int extraLevel) throws Exception {
        HashMap<String,Object> out = new HashMap<>();
        for (Map.Entry<String, Object> entry : damageMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value.getClass()==String.class){
                out.put(key, separateDamage((String) value, extraLevel));
            }
            else {
                if (Objects.equals(key, "level")) {
                    HashMap<String,Object> temp = new HashMap<>();
                    for (int i=1;i<=extraLevel;i++) {
                          temp.put(String.valueOf(i), spellDamageCalculate((HashMap<String, Object>) value, extraLevel));
                    }
                    out.put("extra level:", temp);
                }
                else if (NumberUtils.isCreatable(key)){
                    for (int i =1; i<Integer.parseInt(key)+1;i++){
                        out.put(String.valueOf(i), value);
                    }
                }

                else throw new SpellDamageNotCorrectException();
            }
        }
        return out;
    }

    public static int separateDamage(String damage, int extraLevel){
        int temp=0;
        String[] damageBefore = damage.split("level");
        temp+=damageByOne(damageBefore[0]);
        if(damageBefore.length==1){
            return damageByOne(damage);
        }
        String[] damageByExtraLevels = damageBefore[1].split("d");
        if(damageByExtraLevels[0].charAt(0)=='/'){
            int count = extraLevel/Integer.parseInt(damageByExtraLevels[0].substring(1));
            String newDamage = damageBefore[1].substring(1);
            newDamage = newDamage.replaceFirst(".d", "1d");
            for(int i=0; i<count; i++){
                temp+=damageByOne(newDamage);
            }
        }
        else {
            int count = extraLevel*Integer.parseInt(damageByExtraLevels[0]);
            String newDamage = damageBefore[1];
            newDamage = newDamage.replaceFirst(".d", "1d");
            for(int i=0; i<count; i++){
                temp+=damageByOne(newDamage);
            }
        }
        return temp;
    }
    public static int damageByOne(String damage){
        String[] damageBefore = damage.split("d");
        if (damageBefore.length==1){
        return Integer.parseInt(damageBefore[0]);
        }

        String[] afterSeparation = damageBefore[1].split("\\+");
        int count = Integer.parseInt(damageBefore[0]);
        int rngMax = Integer.parseInt(afterSeparation[0])+1;
        int damageAfter=0;
        for (int ii = 0; ii < count; ii++) {
            Random random = new Random();
            damageAfter += random.ints(1, rngMax)
                    .findAny()
                    .getAsInt();
        }
        if(afterSeparation.length>1){
            damageAfter+=Integer.parseInt(afterSeparation[1]);
        }
        return damageAfter;
    }
}