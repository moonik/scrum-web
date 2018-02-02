package scrumweb.common.asm.common;

public interface FieldElementsAsm<T, Q> {

    T convertToEntityObject(Q element);
    Q convertToDtoObject(T element);
}
