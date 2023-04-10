package fr.lavapower.eogendessentials.command.parser;

public class Result<T>
{
    public T Value;
    public boolean Valid;

    public Result(T value, boolean valid)
    {
        Value = value;
        Valid = valid;
    }
}
