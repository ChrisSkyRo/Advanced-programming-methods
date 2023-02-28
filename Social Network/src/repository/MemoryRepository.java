package repository;

import domain.NetworkEntity;

import java.util.ArrayList;
import java.util.List;

public class MemoryRepository <E extends NetworkEntity> implements Repository<E> {
    protected List<E> repo;

    public MemoryRepository() {
        this.repo = new ArrayList<>();
    }
    
    @Override
    public E addOne(E toAdd) throws RepositoryException {
        if(toAdd == null)
            throw new RepositoryException("NetworkEntity must not be null");
        repo.add(toAdd);
        return repo.get(repo.size()-1);
    }
    
    @Override
    public List<E> addAll(List<E> toAdd) throws RepositoryException {
        for (E entity : toAdd) {
            if(entity == null)
                throw new RepositoryException("NetworkEntity must not be null");
            repo.add(entity);
        }
        return toAdd;
    }
    
    @Override
    public E getOne(Integer ID) throws RepositoryException {
        if(ID == null)
            throw new RepositoryException("ID must not be null");
        for (E entity: repo) {
            if(entity.getID().equals(ID))
                return entity;
        }
        return null;
    }
    
    @Override
    public List<E> getAll() {
        return repo;
    }
    
    @Override
    public void updateOne(E toUpdate) throws RepositoryException {
        if(toUpdate == null)
            throw new RepositoryException("NetworkEntity must not be null");
        for(int i = 0; i < repo.size(); i++) {
            if(repo.get(i).getID().equals(toUpdate.getID())) {
                repo.set(i, toUpdate);
                break;
            }
        }
    }
    
    @Override
    public void updateAll(List<E> toUpdate) {
        for(E NetworkEntity : toUpdate) {
            updateOne(NetworkEntity);
        }
    }
    
    @Override
    public E deleteOne(Integer ID) {
        for(int i = 0; i < repo.size(); i++) {
            if(repo.get(i).getID().equals(ID)) {
                E copy = repo.get(i);
                repo.remove(i);
                return copy;
            }
        }
        return null;
    }
    
    @Override
    public List<E> deleteAll(Integer[] ID) {
        List<E> deletedNetworkEntities = new ArrayList<>();
        for (Integer entityId : ID) {
            for (int i = 0; i < repo.size(); i++) {
                if (repo.get(i).getID().equals(entityId)) {
                    deletedNetworkEntities.add(repo.get(i));
                    repo.remove(i);
                    i--;
                }
            }
        }
        return deletedNetworkEntities;
    }
    
    @Override
    public void clear() {
        repo.clear();
    }
    
    @Override
    public int size() {
        return repo.size();
    }
}
