package mc3eprotocol.payload;

import mc3eprotocol.*;
import mc3eprotocol.define.CommandEnum;
import mc3eprotocol.define.SubCommandEnum;

public class AbnormalResponse extends AbstractResponse {

    public byte[] _payload;

    public AbnormalResponse(byte[] response) {
        parse(response, null);
    }
    
    @Override
    protected void parse(byte[] responseData, AbstractRequest request) {
        _payload = responseData;
    }

    @Override
    public byte[] toBytes() {
        return new byte[]{};
    }

    public void printErrorInfo() {
        CommandEnum command = CommandEnum.build(new byte[]{_payload[5], _payload[6]});
        SubCommandEnum subCommand = SubCommandEnum.build(new byte[]{_payload[7], _payload[8]});

        System.out.println(Utility.hereDoc(s->s, System.lineSeparator(), 
            "Error: " + Utility.fromBytesToHexStringBigEndian(_payload),
            "       1         2   3",
            "       1: アクセス経路",
            "       2: コマンド: " + command + "(" + Utility.fromBytesToHexString(command.getBytes()) + ")",
            "       3: サブコマンド: " + subCommand + "(" + Utility.fromBytesToHexString(subCommand.getBytes()) + ")"
        ));
    }
}
