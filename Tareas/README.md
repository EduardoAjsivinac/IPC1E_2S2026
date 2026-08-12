git config --global user.name "Eduardo Ajsivinac"
git config --global user.email "3113272140409@ingenieria.usac.edu.gt"

# Comprobar que la información sea correcta
git config --global --list

# Generar llave desde el sistema operativo
ssh-keygen -t ed25519 -C "3113272140409@ingenieria.usac.edu.gt"

# Agregar llave a Github.


# Repositorio
git clone git@github.com:EduardoAjsivinac/IPC1E_2S2026.git

# Posicionarse en la rama main
git checkout main

git pull origin main

git branch develop

git checkout develop

# Solo la primera vez en cada rama
git push -u origin develop

# Agregar archivos al stage
git add NOMBRE_ARCHIVO

# Agregar todos los archivos

git add .

# Verificar los archivos del stage
git status

