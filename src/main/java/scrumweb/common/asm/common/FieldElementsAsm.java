package scrumweb.common.asm.common;

public interface FieldElementsAsm<T, Q extends Object> {
    T convertToEntityObject(Q element);
    Q convertToDtoObject(T element);
}
