//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.8
//
// <auto-generated>
//
// Generated from file `auth.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Auth;

public enum TokenVerificationStatus implements java.io.Serializable
{
    Ok(0),
    InvalidToken(1),
    RoleNotAssigned(2);

    public int value()
    {
        return _value;
    }

    public static TokenVerificationStatus valueOf(int v)
    {
        switch(v)
        {
        case 0:
            return Ok;
        case 1:
            return InvalidToken;
        case 2:
            return RoleNotAssigned;
        }
        return null;
    }

    private TokenVerificationStatus(int v)
    {
        _value = v;
    }

    public void ice_write(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeEnum(_value, 2);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, TokenVerificationStatus v)
    {
        if(v == null)
        {
            ostr.writeEnum(Auth.TokenVerificationStatus.Ok.value(), 2);
        }
        else
        {
            ostr.writeEnum(v.value(), 2);
        }
    }

    public static TokenVerificationStatus ice_read(com.zeroc.Ice.InputStream istr)
    {
        int v = istr.readEnum(2);
        return validate(v);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<TokenVerificationStatus> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, TokenVerificationStatus v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.Size))
        {
            ice_write(ostr, v);
        }
    }

    public static java.util.Optional<TokenVerificationStatus> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.Size))
        {
            return java.util.Optional.of(ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static TokenVerificationStatus validate(int v)
    {
        final TokenVerificationStatus e = valueOf(v);
        if(e == null)
        {
            throw new com.zeroc.Ice.MarshalException("enumerator value " + v + " is out of range");
        }
        return e;
    }

    private final int _value;
}
