package helpers;

import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.ChildFriendlinessDaoEnum;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.HillTypeDaoEnum;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.PopularityDaoEnum;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.TransportationTypeDaoEnum;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.helpers.TourDaoModelValidator;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourDaoModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TourDaoModelValidatorTest {
    @Test
    public void testIsValid_ValidModel_ReturnsTrue() {
        // Arrange
        TourDaoModel model = new TourDaoModel(
                "Test Name",
                "Test Description",
                "Test From",
                "Test To",
                TransportationTypeDaoEnum.BIKE,
                100,
                100,
                HillTypeDaoEnum.AVOID_UP_HILL,
                PopularityDaoEnum.NEVER_DONE,
                ChildFriendlinessDaoEnum.NOT_FOR_CHILDREN
        );

        // Act
        boolean result = TourDaoModelValidator.isValid(model);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsValid_InvalidModel_NullValue_ReturnsTrue() {
        // Arrange
        TourDaoModel model = new TourDaoModel(
                null,
                "Test Description",
                "Test From",
                "Test To",
                TransportationTypeDaoEnum.BIKE,
                100,
                100,
                HillTypeDaoEnum.AVOID_UP_HILL,
                PopularityDaoEnum.NEVER_DONE,
                ChildFriendlinessDaoEnum.NOT_FOR_CHILDREN
        );

        // Act
        boolean result = TourDaoModelValidator.isValid(model);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValid_InvalidModel_NameTooLong_ReturnsTrue() {
        // Arrange
        String stringWith60Characters = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce faucibus, nunc a vestibulum ultricies, lacus lacus tempus arcu, ac scelerisque justo augue at arcu.";
        TourDaoModel model = new TourDaoModel(
                stringWith60Characters,
                "Test Description",
                stringWith60Characters,
                stringWith60Characters,
                TransportationTypeDaoEnum.BIKE,
                100,
                100,
                HillTypeDaoEnum.AVOID_UP_HILL,
                PopularityDaoEnum.NEVER_DONE,
                ChildFriendlinessDaoEnum.NOT_FOR_CHILDREN
        );

        // Act
        boolean result = TourDaoModelValidator.isValid(model);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValid_InvalidModel_NegativeNumbers_ReturnsTrue() {
        // Arrange
        TourDaoModel model = new TourDaoModel(
                "Test",
                "Test Description",
                "Test From",
                "Test To",
                TransportationTypeDaoEnum.BIKE,
                -10,
                -10,
                HillTypeDaoEnum.AVOID_UP_HILL,
                PopularityDaoEnum.NEVER_DONE,
                ChildFriendlinessDaoEnum.NOT_FOR_CHILDREN
        );

        // Act
        boolean result = TourDaoModelValidator.isValid(model);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValid_ValidModel_LongDescription_ReturnsTrue() {
        // Arrange
        String stringWith60Characters = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce faucibus, nunc a vestibulum ultricies, lacus lacus tempus arcu, ac scelerisque justo augue at arcu.";
        TourDaoModel model = new TourDaoModel(
                "Test",
                stringWith60Characters,
                "Test From",
                "Test To",
                TransportationTypeDaoEnum.BIKE,
                100,
                100,
                HillTypeDaoEnum.AVOID_UP_HILL,
                PopularityDaoEnum.NEVER_DONE,
                ChildFriendlinessDaoEnum.NOT_FOR_CHILDREN
        );

        // Act
        boolean result = TourDaoModelValidator.isValid(model);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsValid_NullModel_ReturnsFalse() {
        // Arrange
        TourDaoModel model = null;

        // Act
        boolean result = TourDaoModelValidator.isValid(model);

        // Assert
        assertFalse(result);
    }
}
