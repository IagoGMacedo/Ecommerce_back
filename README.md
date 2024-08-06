# Ecommerce_back

### Pontos implementados: 

 1. Solução funcional e com objetivo explícito. Deve dividir, de maneira adequada,
 componentes importantes da aplicação, front e back, criando seus próprios pacotes e
 subdiretórios. Todos os serviços de sua aplicação devem seguir a arquitetura MVC.
 Para cada modelo operacional, implementar o respectivo controller- rest controller
que deve receber as requisições e respondê-las. NÃO implementar lógica e regras de
 negócio nos controllers. Para isso, você deve usar os services, desacoplando sua
 aplicação das classes repositórios ou de regras de negócio.
 2. O sistema deve fazer extenso uso do padrão arquitetural de microsserviços. Você deve
 escalar sua aplicação em, pelo menos, 4 microsserviços principais- núcleo das
 funções (não contabiliza api gateway, serviço de descoberta e serviço de
 configuração). Os microsserviços devem se registrar em um serviço de descoberta.
 Além disso, deve existir API gateway sendo ponto único de comunicação do cliente
 com os outros serviços da sua aplicação.
 3. Todos os serviços devem obter suas configurações a partir de um servidor de
 configuração integrado a um repositório no Git. Informações relacionadas a bancos,
 acessos, strings sensíveis devem ficar em um arquivo de perfil de acordo com uso e
 serem carregadas pelo serviço de configuração.
 4. Segurança: você deve implementar camada de segurança no acesso aos recursos dos
 seus microsserviços. Autenticar os usuários que terão acesso. Deve existir pelo menos
 4 papéis de usuários no seu sistema que terão permissões de acesso personalizadas.
 Você deve utilizar tokens para manter seus usuários autenticados- recomendo o JWT.
 Otoken deve ter um prazo de expiração, sendo necessário uma nova autenticação com
 login e senha. Pelo menos um conjunto de requisições de uma entidade deve ser
 habilitada só a usuários com papel de administrador ou a mais de um papel. 
