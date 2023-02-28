package domain.validators;

import domain.Friendship;

import java.time.LocalDateTime;

public class FriendshipValidator implements Validator<Friendship> {
    /**
     * Validates a friendship
     * @param friendship the friendship to validate
     * @throws ValidatorException if friendship is invalid
     */
    public void validate(Friendship friendship, ValidatorStrategy strategy) throws ValidatorException {
        if(friendship.getDateCreated().compareTo(LocalDateTime.now()) > 0 || friendship.getUser1().equals(friendship.getUser2()))
            throw new ValidatorException("Invalid friendship");
    }
}
