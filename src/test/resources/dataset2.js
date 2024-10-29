db = connect('mongodb://localhost:27017/proyectotest?authSource=admin');

db.business.insertMany([
    {
        _id: 'Negocio21',
        name: 'Restaurante Mexicano',
        description: 'Restaurante de comida mexicana en Armenia',
        idClient: 'Cliente1',
        location: {
            latitude: 4.540130,
            longitude: -75.665660
        },
        images: ['imagen1', 'imagen2'],
        typeBusiness: 'RESTAURANTE',
        timeSchedules: [
            {
                day: 'LUNES',
                start: '08:00',
                end: '20:00'
            }
        ],
        phone: ['1234567', '7654321'],
        state: 'ACTIVE',
        open: false,
        _class: 'co.edu.uniquindio.proyecto.model.documents.Business'
    },
    {
        _id: 'Negocio22',
        name: 'Salón de Belleza Elegance',
        description: 'Servicios de belleza y cuidado personal',
        idClient: 'Cliente1',
        location: {
            latitude: 4.531900,
            longitude: -75.660300
        },
        images: ['imagen13', 'imagen14'],
        typeBusiness: 'SALON_BELLEZA',
        timeSchedules: [
            {
                day: 'DOMINGO',
                start: '09:00',
                end: '15:00'
            }
        ],
        phone: ['3071122334'],
        state: 'APPROVED',
        open: false,
        _class: 'co.edu.uniquindio.proyecto.model.documents.Business'
    }
]);
