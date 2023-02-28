package repository;

import domain.NetworkEntity;

import java.util.List;

public interface Repository<E extends NetworkEntity> {
    /**
     * Adds a NetworkEntity to the repository
     * @param toAdd the NetworkEntity to add
     * @return the added NetworkEntity
     */
    E addOne(E toAdd) throws RepositoryException;

    /**
     * Adds NetworkEntities from a list to the repository
     * @param toAdd the list of NetworkEntities to add
     * @return the NetworkEntities that were added to the list
     */
    List<E> addAll(List<E> toAdd) throws RepositoryException;

    /**
     * Gets the NetworkEntity with the given ID
     * @param ID the ID of the NetworkEntity to get
     * @return the NetworkEntity with given ID or null if it doesn't exist
     */
    E getOne(Integer ID) throws RepositoryException;

    /**
     * Gets a list of containing all the NetworkEntities in the repository
     * @return the list of NetworkEntities
     */
    List<E> getAll();

    /**
     * Updates a NetworkEntity
     * @param toUpdate the NetworkEntity to update
     */
    void updateOne(E toUpdate) throws RepositoryException;

    /**
     * Updates all the NetworkEntities in the array
     * @param toUpdate the array of NetworkEntities to update
     */
    void updateAll(List<E> toUpdate);

    /**
     * Deletes a NetworkEntity with given ID
     * @param ID the ID of the NetworkEntity to delete
     * @return the deleted NetworkEntity or null if the NetworkEntity doesn't exist
     */
    E deleteOne(Integer ID);

    /**
     * Deletes all the NetworkEntities with given IDs
     * @param ID array of IDs of NetworkEntities to delete
     * @return a list of the deleted NetworkEntities
     */
    List<E> deleteAll(Integer[] ID);

    /**
     * Clears the repository (deletes all NetworkEntities)
     */
    void clear();

    /**
     * Returns the size of the repository
     * @return the size of the list of NetworkEntities
     */
    int size();
}
