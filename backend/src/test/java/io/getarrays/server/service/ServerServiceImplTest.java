package io.getarrays.server.service;

import io.getarrays.server.model.Server;
import io.getarrays.server.repo.ServerRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import static io.getarrays.server.enumeration.Status.SERVER_DOWN;
import static io.getarrays.server.enumeration.Status.SERVER_UP;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ServerServiceImplTest {

    @Mock
    private ServerRepository serverRepository;
    private AutoCloseable autoCloseable;
    private ServerServiceImpl underTest;
    private Server server;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new ServerServiceImpl(serverRepository);
        server = new Server(null, "10.7.14.99", "Ubuntu Linux", "16 GB",
                "Personal PC", null, null);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void createNewServer() {
        //given
        HttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
        //when
        underTest.create(server);
        //then
        verify(serverRepository).save(server);
        assertThat(server.getImageUrl()).isNotNull();
    }

    @Test
    void getServer() {
        //given
        Long id = 13L;
        //when
        when(serverRepository.getById(id)).thenReturn(server);
        underTest.get(id);
        //then
        verify(serverRepository).findById(id);
        assertThat(server).isNotNull();
    }

    @Test
    void pingReachableIpAddress() throws IOException {
        //given
        String ipAddress = "10.7.14.99";
        //when
        when(serverRepository.findByIpAddress(ipAddress)).thenReturn(server);
        Server server = underTest.ping(ipAddress);
        //then
        verify(serverRepository).save(server);
        verify(serverRepository).findByIpAddress(ipAddress);
        assertThat(server.getStatus()).isEqualTo(SERVER_UP);
    }

    @Test
    void pingUnReachableIpAddress() throws IOException {
        //given
        String ipAddress = "10.77.88.88";
        //when
        when(serverRepository.findByIpAddress(ipAddress)).thenReturn(server);
        Server server = underTest.ping(ipAddress);
        //then
        verify(serverRepository).save(server);
        verify(serverRepository).findByIpAddress(ipAddress);
        assertThat(server.getStatus()).isEqualTo(SERVER_DOWN);
    }

    @Test
    void canGetAllServers() {
        //when
        underTest.list(10);
        //then
        verify(serverRepository).findAll(PageRequest.of(0, 10));
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}