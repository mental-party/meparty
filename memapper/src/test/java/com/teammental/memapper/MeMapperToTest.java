package com.teammental.memapper;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.teammental.memapper.to.BooleanTypesTo;
import com.teammental.memapper.to.EnumGender;
import com.teammental.memapper.to.NameTo;
import com.teammental.memapper.to.PrimitiveTypeTo;
import com.teammental.memapper.to.TargetPersonNoEmptyConstructorTo;
import com.teammental.memapper.to.TargetPersonTo;
import com.teammental.memapper.to.TrainerPersonTo;
import com.teammental.memapper.to.WrapperTypeTo;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class MeMapperToTest {

  @RunWith(Enclosed.class)
  public static class WhenTargetClassIsGiven {

    private static final Integer ID = 31648;
    private static final String TITLE = "TITLE";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String MIDDLE_NAME = "MIDDLE_NAME";
    private static final String LAST_NAME = "LAST_NAME";
    private static final EnumGender GENDER = EnumGender.FEMALE;
    private static final List<String> LESSONS = Arrays.asList("a", "b", "c", "d", "e");
    private static final Double SALARY = 5246.67;


    @Ignore
    public static class WithoutOptionalUsage {

      @Test
      public void shouldMapAllFields() {
        TrainerPersonTo trainerPersonTo = prepareTrainer();

        TargetPersonTo targetPersonTo = (TargetPersonTo) MeMapper.from(trainerPersonTo)
            .to(TargetPersonTo.class);

        assertEquals(trainerPersonTo.getLessons(), targetPersonTo.getLessons());
        assertEquals(trainerPersonTo.getSalary(), targetPersonTo.getSalary());
        assertEquals(trainerPersonTo.getGender(), targetPersonTo.getGender());
        assertEquals(trainerPersonTo.getId(), targetPersonTo.getId());
        assertEquals(trainerPersonTo.getName(), targetPersonTo.getName());
        assertEquals(trainerPersonTo.getTitle(), targetPersonTo.getTitle());
      }

      @Test
      public void shouldReturnNull_whenTargetHasNotEmptyConstructor() {
        TrainerPersonTo trainerPersonTo = prepareTrainer();

        TargetPersonNoEmptyConstructorTo trainerPersonToTarget = (TargetPersonNoEmptyConstructorTo)
            MeMapper.from(trainerPersonTo)
                .to(TargetPersonNoEmptyConstructorTo.class);

        assertNull(trainerPersonToTarget);
      }

    }

    @Ignore
    public static class WithOptionalUsage {

      @Test
      public void shouldMapAllFields() {
        TrainerPersonTo trainerPersonTo = prepareTrainer();

        Optional<TargetPersonTo> targetPersonToOptional = MeMapper.from(trainerPersonTo)
            .toOptional(TargetPersonTo.class);

        TargetPersonTo targetPersonTo = targetPersonToOptional.get();
        assertEquals(trainerPersonTo.getLessons(), targetPersonTo.getLessons());
        assertEquals(trainerPersonTo.getSalary(), targetPersonTo.getSalary());
        assertEquals(trainerPersonTo.getGender(), targetPersonTo.getGender());
        assertEquals(trainerPersonTo.getId(), targetPersonTo.getId());
        assertEquals(trainerPersonTo.getName(), targetPersonTo.getName());
        assertEquals(trainerPersonTo.getTitle(), targetPersonTo.getTitle());
      }

      @Test
      public void shouldReturnEmpty_whenTargetHasNotEmptyConstructor() {
        TrainerPersonTo trainerPersonTo = prepareTrainer();

        Optional<TargetPersonNoEmptyConstructorTo> optional =
            MeMapper.from(trainerPersonTo)
                .toOptional(TargetPersonNoEmptyConstructorTo.class);

        assertFalse(optional.isPresent());
      }

    }

    private static TrainerPersonTo prepareTrainer() {
      TrainerPersonTo trainer = new TrainerPersonTo();

      trainer.setId(ID);
      trainer.setTitle(TITLE);
      trainer.setName(new NameTo(FIRST_NAME, MIDDLE_NAME, LAST_NAME));
      trainer.setLessons(LESSONS);
      trainer.setSalary(SALARY);
      trainer.setGender(GENDER);

      return trainer;
    }

  }

  @RunWith(Enclosed.class)
  public static class ConversionOfPrimitiveVsWrapperTypes {

    public static class WithoutOptionalUsage {
      @Test
      public void shouldMapAllPrimitivesToWrapperTypes() {
        int id = 1;
        short sid = 2;
        long lid = 3L;
        float fid = 4f;
        double did = 5.0;
        char cid = 'a';
        boolean bid = true;
        byte byid = 6;

        PrimitiveTypeTo primitiveTypeTo = new PrimitiveTypeTo();
        primitiveTypeTo.setBid(bid);
        primitiveTypeTo.setByid(byid);
        primitiveTypeTo.setCid(cid);
        primitiveTypeTo.setDid(did);
        primitiveTypeTo.setFid(fid);
        primitiveTypeTo.setId(id);
        primitiveTypeTo.setLid(lid);
        primitiveTypeTo.setSid(sid);

        WrapperTypeTo wrapperTypeTo = (WrapperTypeTo)
            MeMapper.from(primitiveTypeTo).to(WrapperTypeTo.class);

        assertNotNull(wrapperTypeTo);
        assertEquals((Integer) id, wrapperTypeTo.getId());
        assertEquals((Short) sid, wrapperTypeTo.getSid());
        assertEquals((Long) lid, wrapperTypeTo.getLid());
        assertEquals((Float) fid, wrapperTypeTo.getFid());
        assertEquals((Double) did, wrapperTypeTo.getDid());
        assertEquals((Character) cid, wrapperTypeTo.getCid());
        assertEquals((Boolean) bid, wrapperTypeTo.getBid());
        assertEquals((Byte) byid, wrapperTypeTo.getByid());
      }

      @Test
      public void shouldMapAllWrappersToPrimitiveTypes() {
        Integer id = 1;
        Short sid = 2;
        Long lid = 3L;
        Float fid = 4f;
        Double did = 5.0;
        Character cid = 'a';
        Boolean bid = true;
        Byte byid = 6;

        WrapperTypeTo wrapperTypeTo = new WrapperTypeTo();
        wrapperTypeTo.setBid(bid);
        wrapperTypeTo.setByid(byid);
        wrapperTypeTo.setCid(cid);
        wrapperTypeTo.setDid(did);
        wrapperTypeTo.setFid(fid);
        wrapperTypeTo.setId(id);
        wrapperTypeTo.setLid(lid);
        wrapperTypeTo.setSid(sid);

        PrimitiveTypeTo primitiveTypeTo = (PrimitiveTypeTo)
            MeMapper.from(wrapperTypeTo).to(PrimitiveTypeTo.class);

        assertNotNull(primitiveTypeTo);
        assertEquals((int) id, primitiveTypeTo.getId());
        assertEquals((short) sid, primitiveTypeTo.getSid());
        assertEquals((long) lid, primitiveTypeTo.getLid());
        assertEquals((float) fid, primitiveTypeTo.getFid(), 0);
        assertEquals((double) did, primitiveTypeTo.getDid(), 0);
        assertEquals((char) cid, primitiveTypeTo.getCid());
        assertEquals(bid, primitiveTypeTo.getBid());
        assertEquals((byte) byid, primitiveTypeTo.getByid());
      }
    }

    public static class WithOptionalUsage {
      @Test
      public void shouldMapAllPrimitivesToWrapperTypes() {
        int id = 1;
        short sid = 2;
        long lid = 3L;
        float fid = 4f;
        double did = 5.0;
        char cid = 'a';
        boolean bid = true;
        byte byid = 6;

        PrimitiveTypeTo primitiveTypeTo = new PrimitiveTypeTo();
        primitiveTypeTo.setBid(bid);
        primitiveTypeTo.setByid(byid);
        primitiveTypeTo.setCid(cid);
        primitiveTypeTo.setDid(did);
        primitiveTypeTo.setFid(fid);
        primitiveTypeTo.setId(id);
        primitiveTypeTo.setLid(lid);
        primitiveTypeTo.setSid(sid);

        Optional<WrapperTypeTo> wrapperTypeToOptional =
            MeMapper.from(primitiveTypeTo).toOptional(WrapperTypeTo.class);

        assertTrue(wrapperTypeToOptional.isPresent());
        WrapperTypeTo wrapperTypeTo = wrapperTypeToOptional.get();
        assertEquals((Integer) id, wrapperTypeTo.getId());
        assertEquals((Short) sid, wrapperTypeTo.getSid());
        assertEquals((Long) lid, wrapperTypeTo.getLid());
        assertEquals((Float) fid, wrapperTypeTo.getFid());
        assertEquals((Double) did, wrapperTypeTo.getDid());
        assertEquals((Character) cid, wrapperTypeTo.getCid());
        assertEquals((Boolean) bid, wrapperTypeTo.getBid());
        assertEquals((Byte) byid, wrapperTypeTo.getByid());
      }

      @Test
      public void shouldMapAllWrappersToPrimitiveTypes() {
        Integer id = 1;
        Short sid = 2;
        Long lid = 3L;
        Float fid = 4f;
        Double did = 5.0;
        Character cid = 'a';
        Boolean bid = true;
        Byte byid = 6;

        WrapperTypeTo wrapperTypeTo = new WrapperTypeTo();
        wrapperTypeTo.setBid(bid);
        wrapperTypeTo.setByid(byid);
        wrapperTypeTo.setCid(cid);
        wrapperTypeTo.setDid(did);
        wrapperTypeTo.setFid(fid);
        wrapperTypeTo.setId(id);
        wrapperTypeTo.setLid(lid);
        wrapperTypeTo.setSid(sid);

        Optional<PrimitiveTypeTo> primitiveTypeToOptional =
            MeMapper.from(wrapperTypeTo).toOptional(PrimitiveTypeTo.class);

        assertTrue(primitiveTypeToOptional.isPresent());
        PrimitiveTypeTo primitiveTypeTo = primitiveTypeToOptional.get();
        assertEquals((int) id, primitiveTypeTo.getId());
        assertEquals((short) sid, primitiveTypeTo.getSid());
        assertEquals((long) lid, primitiveTypeTo.getLid());
        assertEquals((float) fid, primitiveTypeTo.getFid(), 0);
        assertEquals((double) did, primitiveTypeTo.getDid(), 0);
        assertEquals((char) cid, primitiveTypeTo.getCid());
        assertEquals(bid, primitiveTypeTo.getBid());
        assertEquals((byte) byid, primitiveTypeTo.getByid());
      }
    }
  }

  @RunWith(Enclosed.class)
  public static class ConversionOfBooleanField {

    public static class WithoutOptionalUsage {
      @Test
      public void shouldMap_whenGetterNameBeginsWithIsAndPrimitive() {
        final boolean value = true;

        BooleanTypesTo booleanTypesTo = new BooleanTypesTo();
        booleanTypesTo.setNameBeginsWithIsAndPrimitive(value);

        BooleanTypesTo mappedBooleanTypesTo = (BooleanTypesTo) MeMapper.from(booleanTypesTo)
            .to(BooleanTypesTo.class);

        assertEquals(value, mappedBooleanTypesTo.isNameBeginsWithIsAndPrimitive());
      }

      @Test
      public void shouldMap_whenGetterNameBeginsWithIsAndWrapper() {
        final Boolean value = true;

        BooleanTypesTo booleanTypesTo = new BooleanTypesTo();
        booleanTypesTo.setNameBeginsWithIsAndWrapper(value);

        BooleanTypesTo mappedBooleanTypesTo = (BooleanTypesTo) MeMapper.from(booleanTypesTo)
            .to(BooleanTypesTo.class);

        assertEquals(value, mappedBooleanTypesTo.isNameBeginsWithIsAndWrapper());
      }

      @Test
      public void shouldMap_whenGetterNameBeginsWithGetAndPrimitive() {
        final boolean value = true;

        BooleanTypesTo booleanTypesTo = new BooleanTypesTo();
        booleanTypesTo.setNameBeginsWithGetAndPrimitive(value);

        BooleanTypesTo mappedBooleanTypesTo = (BooleanTypesTo) MeMapper.from(booleanTypesTo)
            .to(BooleanTypesTo.class);

        assertEquals(value, mappedBooleanTypesTo.getNameBeginsWithGetAndPrimitive());
      }

      @Test
      public void shouldMap_whenGetterNameBeginsWithGetAndWrapper() {
        final Boolean value = true;

        BooleanTypesTo booleanTypesTo = new BooleanTypesTo();
        booleanTypesTo.setNameBeginsWithGetAndWrapper(value);

        BooleanTypesTo mappedBooleanTypesTo = (BooleanTypesTo) MeMapper.from(booleanTypesTo)
            .to(BooleanTypesTo.class);

        assertEquals(value, mappedBooleanTypesTo.getNameBeginsWithGetAndWrapper());
      }
    }

    public static class WithOptionalUsage {
      @Test
      public void shouldMap_whenGetterNameBeginsWithIsAndPrimitive() {
        final boolean value = true;

        BooleanTypesTo booleanTypesTo = new BooleanTypesTo();
        booleanTypesTo.setNameBeginsWithIsAndPrimitive(value);

        Optional<BooleanTypesTo> mappedBooleanTypesTo = MeMapper.from(booleanTypesTo)
            .toOptional(BooleanTypesTo.class);

        assertEquals(value, mappedBooleanTypesTo.get().isNameBeginsWithIsAndPrimitive());
      }

      @Test
      public void shouldMap_whenGetterNameBeginsWithIsAndWrapper() {
        final Boolean value = true;

        BooleanTypesTo booleanTypesTo = new BooleanTypesTo();
        booleanTypesTo.setNameBeginsWithIsAndWrapper(value);

        Optional<BooleanTypesTo> mappedBooleanTypesTo = MeMapper.from(booleanTypesTo)
            .toOptional(BooleanTypesTo.class);

        assertEquals(value, mappedBooleanTypesTo.get().isNameBeginsWithIsAndWrapper());
      }

      @Test
      public void shouldMap_whenGetterNameBeginsWithGetAndPrimitive() {
        final boolean value = true;

        BooleanTypesTo booleanTypesTo = new BooleanTypesTo();
        booleanTypesTo.setNameBeginsWithGetAndPrimitive(value);

        Optional<BooleanTypesTo> mappedBooleanTypesTo = MeMapper.from(booleanTypesTo)
            .toOptional(BooleanTypesTo.class);

        assertEquals(value, mappedBooleanTypesTo.get().getNameBeginsWithGetAndPrimitive());
      }

      @Test
      public void shouldMap_whenGetterNameBeginsWithGetAndWrapper() {
        final Boolean value = true;

        BooleanTypesTo booleanTypesTo = new BooleanTypesTo();
        booleanTypesTo.setNameBeginsWithGetAndWrapper(value);

        Optional<BooleanTypesTo> mappedBooleanTypesTo = MeMapper.from(booleanTypesTo)
            .toOptional(BooleanTypesTo.class);

        assertEquals(value, mappedBooleanTypesTo.get().getNameBeginsWithGetAndWrapper());
      }
    }
  }
}
