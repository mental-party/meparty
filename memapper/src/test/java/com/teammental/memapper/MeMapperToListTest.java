package com.teammental.memapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.teammental.memapper.to.EnumGender;
import com.teammental.memapper.to.NameTo;
import com.teammental.memapper.to.TargetPersonNoEmptyConstructorTo;
import com.teammental.memapper.to.TargetPersonTo;
import com.teammental.memapper.to.TrainerPersonTo;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class MeMapperToListTest {

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


    public static class WithoutOptionalUsage {

      @Test
      public void shouldMapAllItemInList() {
        final int size = 20;
        List<TrainerPersonTo> trainerPersonTos = prepareTrainerList(size);

        List<TargetPersonTo> targetPersonTos = (List<TargetPersonTo>) MeMapper.from(trainerPersonTos)
            .to(TargetPersonTo.class);

        assertEquals(size, targetPersonTos.size());
      }

    }

    public static class WithOptionalUsage {

      @Test
      public void shouldMapAllItemInList() {
        final int size = 20;
        List<TrainerPersonTo> trainerPersonTos = prepareTrainerList(size);

        Optional<List<TargetPersonTo>> optional = MeMapper.from(trainerPersonTos)
            .toOptional(TargetPersonTo.class);

        List<TargetPersonTo> targetPersonTos = optional.get();

        assertEquals(size, targetPersonTos.size());

        assertEquals(trainerPersonTos.get(0).getId(), targetPersonTos.get(0).getId());
      }
    }


    private static List<TrainerPersonTo> prepareTrainerList(int size) {
      List<TrainerPersonTo> trainers = new ArrayList<>();

      for (int i = 0; i < size; i++) {
        TrainerPersonTo trainer = new TrainerPersonTo();
        trainer.setId(ID + i);
        trainer.setTitle(TITLE + "_" + i);
        trainer.setName(new NameTo(FIRST_NAME + "_" + i,
            MIDDLE_NAME + "_" + i, LAST_NAME + "_" + i));
        trainer.setLessons(LESSONS);
        trainer.setSalary(SALARY);
        trainer.setGender(GENDER);
        trainers.add(trainer);
      }
      return trainers;
    }
  }
}